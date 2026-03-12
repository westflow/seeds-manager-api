# Guia de Arquitetura e Padrões de Desenvolvimento
## Seeds Manager API

**Objetivo**: Manter a consistência arquitetural e de codificação do projeto em todas as contribuições.

---

## 1. Visão Geral da Arquitetura

O projeto utiliza **Arquitetura Hexagonal (Ports & Adapters)** com camadas bem definidas:

```
┌─────────────────────────────────────────┐
│            API (Controllers)             │
│    (DTOs, Mappers, Exception Handlers)   │
├─────────────────────────────────────────┤
│         APPLICATION (Use Cases)          │
│  (Orquestração de regras de negócio)     │
├─────────────────────────────────────────┤
│      DOMAIN (Entidades e Regras)         │
│  (Models, Repositories, Exceptions)      │
├─────────────────────────────────────────┤
│      INFRASTRUCTURE (Implementações)     │
│  (Persistência, Segurança, Configurações)│
└─────────────────────────────────────────┘
```

---

## 2. Estrutura de Diretórios

```
src/main/java/com/westflow/seeds_manager_api/
├── api/
│   ├── config/                  # Configurações da API (OpenAPI, autenticação)
│   ├── controller/              # REST Controllers
│   ├── dto/
│   │   ├── request/             # DTOs de entrada
│   │   └── response/            # DTOs de saída
│   ├── exception/               # Tratamento de exceções (Handlers, DTOs de erro)
│   └── mapper/                  # Mappers (DTOs <-> Domain)
│
├── application/
│   ├── usecase/                 # Casos de uso (agrupados por domínio)
│   │   ├── seed/
│   │   ├── lot/
│   │   ├── invoice/
│   │   ├── user/
│   │   ├── auth/
│   │   ├── client/
│   │   ├── company/
│   │   ├── lab/
│   │   ├── bagtype/
│   │   └── bagweight/
│   ├── factory/                 # Factories para criação de objetos complexos
│   ├── port/                    # Portas (interfaces que não são repositories)
│   └── support/                 # Serviços de suporte (contextos, preparações)
│
├── domain/
│   ├── model/                   # Entidades de domínio
│   ├── repository/              # Interfaces de repositório (contratos)
│   ├── service/                 # Serviços de domínio (lógica complexa do negócio)
│   ├── event/                   # Eventos de domínio
│   ├── enums/                   # Enumerações de domínio
│   └── exception/               # Exceções de domínio
│
└── infrastructure/
    ├── config/                  # Configurações do Spring (Security, Mail, etc)
    ├── persistence/
    │   ├── entity/              # Entidades JPA
    │   ├── repository/          # Repositórios Spring Data JPA
    │   ├── adapter/             # Adaptadores (implementação de repositories)
    │   ├── mapper/              # Mappers (Domain <-> Entity)
    │   └── specification/       # Specifications para queries dinâmicas
    ├── security/                # Implementações de segurança
    ├── web/                     # Filtros e utilitários web (XSS, sanitização)
    ├── mail/                    # Serviço de e-mail
    ├── event/                   # Listeners de eventos
    └── seed/                    # Inicializadores de dados
```

---

## 3. Padrões de Codificação

### 3.1 Entidades de Domínio (Domain Models)

**Localização**: `domain/model/*.java`

**Características**:
- Contêm a lógica de negócio principal
- Usam `@Getter` (Lombok) para acesso aos atributos
- Construtores protegidos via `@AllArgsConstructor(access = AccessLevel.PROTECTED)`
- Devem ter métodos factory (`newXxx()`) para criar novas instâncias
- Devem ter métodos `restore()` para reconstruir a partir de dados persistidos

**Exemplo**:
```java
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seed {
    private Long id;
    private String species;
    private String cultivar;
    private boolean isProtected;
    private Boolean active = true;
    
    // Factory para criar novas instâncias
    public static Seed newSeed(String species, String cultivar, boolean isProtected) {
        validateStatic(species, cultivar);
        Seed seed = new Seed();
        seed.id = null;
        seed.species = species;
        seed.cultivar = cultivar;
        seed.isProtected = isProtected;
        return seed;
    }
    
    // Método para restaurar a partir do banco
    public static Seed restore(Long id, String species, ...) {
        Seed seed = new Seed();
        // ... populando dados
        return seed;
    }
    
    // Métodos de comportamento
    public void update(String species, String cultivar, boolean isProtected) {
        // ... lógica de atualização
    }
    
    public void deactivate() {
        if (!active) {
            throw new ValidationException("A semente já está deletada.");
        }
        this.active = false;
    }
}
```

---

### 3.2 Exceções de Domínio

**Localização**: `domain/exception/*.java`

**Hierarquia**:
```
DomainException (base)
├── ValidationException
├── BusinessException
├── ResourceNotFoundException
├── UnauthorizedException
├── InsufficientLotBalanceException
└── DuplicateInvoiceNumberException
```

**Uso**:
- `ValidationException`: Erros de validação de dados
- `BusinessException`: Violações de regras de negócio (CONFLICT - 409)
- `ResourceNotFoundException`: Recurso não encontrado (NOT_FOUND - 404)
- `UnauthorizedException`: Acesso negado (UNAUTHORIZED - 401)

**Exemplo**:
```java
public Seed execute(Seed seed) {
    seedRepository.findByNormalizedSpeciesAndNormalizedCultivar(...)
        .ifPresent(existing -> {
            throw new BusinessException("Já existe uma semente com essa espécie e cultivar.");
        });
    return seedRepository.save(seed);
}
```

---

### 3.3 Repositórios (Ports)

**Localização**: 
- Interface: `domain/repository/*.java`
- Implementação: `infrastructure/persistence/adapter/*.java`

**Características**:
- Interfaces no domínio (definem contratos)
- Implementações na infraestrutura (adaptadores)
- Usam Adapter Pattern
- Mapeiam Domain Models <-> Entities JPA

**Exemplo**:
```java
// Interface no domínio
public interface SeedRepository {
    Seed save(Seed seed);
    Optional<Seed> findById(Long id);
    Optional<Seed> findByNormalizedSpeciesAndNormalizedCultivar(String normalizedSpecies, String normalizedCultivar);
    Page<Seed> findAll(Boolean isProtected, Pageable pageable);
}

// Implementação na infraestrutura
@Component
public class SeedRepositoryAdapter implements SeedRepository {
    private final JpaSeedRepository jpaRepository;
    private final SeedPersistenceMapper mapper;
    
    @Override
    public Seed save(Seed seed) {
        SeedEntity entity = mapper.toEntity(seed);
        return mapper.toDomain(jpaRepository.save(entity));
    }
    
    @Override
    public Page<Seed> findAll(Boolean isProtected, Pageable pageable) {
        Specification<SeedEntity> spec = SeedSpecifications.hasProtected(isProtected)
            .and(SeedSpecifications.isActive());
        return jpaRepository.findAll(spec, pageable).map(mapper::toDomain);
    }
}
```

---

### 3.4 Casos de Uso (Use Cases)

**Localização**: `application/usecase/{domínio}/*UseCase.java`

**Características**:
- Uma classe por caso de uso (responsabilidade única)
- Anotação `@Component` (ou `@Service`)
- Método principal: `execute(...)`
- Injetam repositórios e serviços do domínio
- Orquestram operações de negócio

**Padrão de Nomenclatura**:
- `Register*UseCase`: Criar novo recurso
- `Find*UseCase`: Consultar recursos
- `Update*UseCase`: Atualizar recurso
- `Delete*UseCase`: Deletar recurso
- `*ByIdUseCase`: Buscar por ID
- `FindPagedUseCase`: Buscar com paginação

**Exemplo**:
```java
@Component
@RequiredArgsConstructor
public class RegisterSeedUseCase {
    
    private final SeedRepository seedRepository;
    
    public Seed execute(Seed seed) {
        seedRepository.findByNormalizedSpeciesAndNormalizedCultivar(
                seed.getNormalizedSpecies(),
                seed.getNormalizedCultivar())
            .ifPresent(existing -> {
                throw new BusinessException("Já existe uma semente com essa espécie e cultivar.");
            });
        
        return seedRepository.save(seed);
    }
}
```

---

### 3.5 Controllers (REST)

**Localização**: `api/controller/*Controller.java`

**Características**:
- Anotação `@RestController`
- Anotação `@RequestMapping` com path base
- Anotação `@Tag` (OpenAPI) para documentação
- Métodos com `@Operation` e `@ApiResponse`
- Utilizam `@PreAuthorize` para controle de acesso
- Injetam mappers e use cases

**Padrão REST**:
- `POST /resource` → Criar (201 CREATED)
- `GET /resource` → Listar com paginação (200 OK)
- `GET /resource/{id}` → Buscar um (200 OK)
- `PUT /resource/{id}` → Atualizar (200 OK)
- `DELETE /resource/{id}` → Deletar (204 NO CONTENT)

**Exemplo**:
```java
@RestController
@RequiredArgsConstructor
@RequestMapping("/seeds")
@Tag(name = "Seeds", description = "Operações de sementes")
public class SeedController {

    private final SeedMapper mapper;
    private final RegisterSeedUseCase registerSeedUseCase;
    private final FindPagedSeedsUseCase findPagedSeedsUseCase;
    private final FindSeedByIdUseCase findSeedByIdUseCase;
    private final UpdateSeedUseCase updateSeedUseCase;
    private final DeleteSeedUseCase deleteSeedUseCase;

    @Operation(
        summary = "Cria uma nova semente",
        description = "Valida e persiste uma semente no sistema",
        responses = {
            @ApiResponse(responseCode = "201", description = "Semente criada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
        }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SeedResponse> register(@Valid @RequestBody SeedRequest request) {
        Seed seed = Seed.newSeed(
            request.getSpecies(),
            request.getCultivar(),
            request.isProtected()
        );
        
        Seed saved = registerSeedUseCase.execute(seed);
        SeedResponse response = mapper.toResponse(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Lista todas as sementes")
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD', 'READ_ONLY')")
    @GetMapping
    public ResponseEntity<Page<SeedResponse>> listAll(
            @ParameterObject Pageable pageable,
            @RequestParam(value = "protected", required = false) Boolean isProtected) {
        Page<Seed> page = findPagedSeedsUseCase.execute(isProtected, pageable);
        Page<SeedResponse> response = page.map(mapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteSeedUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

### 3.6 DTOs (Data Transfer Objects)

**Localização**:
- Request: `api/dto/request/*Request.java`
- Response: `api/dto/response/*Response.java`

**Características**:
- Anotações `@Getter`, `@Setter` (Lombok)
- Anotações `@Schema` (OpenAPI)
- Validações com `@NotBlank`, `@Pattern`, `@Size`, etc.
- DTOs Request com validação
- DTOs Response com documentação

**Exemplo Request**:
```java
@Setter
@Getter
@Schema(name = "SeedRequest", description = "Payload para criação de semente")
public class SeedRequest {

    @Schema(
        description = "Espécie da semente",
        example = "Urochloa Brizantha",
        requiredMode = RequiredMode.REQUIRED
    )
    @NotBlank(message = "Espécie é obrigatória")
    @Size(max = 100, message = "A espécie deve ter no máximo 100 caracteres")
    @Pattern(regexp = "^[\\p{L}0-9 .-]+", message = "A espécie contém caracteres inválidos")
    private String species;

    @Schema(
        description = "Cultivar da semente",
        example = "Marandu",
        requiredMode = RequiredMode.REQUIRED
    )
    @NotBlank(message = "Cultivar é obrigatória")
    @Size(max = 100, message = "O cultivar deve ter no máximo 100 caracteres")
    private String cultivar;

    @Schema(description = "Indica se a semente é protegida", example = "false")
    private boolean isProtected;
}
```

---

### 3.7 Mappers

**Localização**:
- API: `api/mapper/*Mapper.java`
- Persistência: `infrastructure/persistence/mapper/*PersistenceMapper.java`

**Características**:
- Interface anotada com `@Mapper(componentModel = "spring")`
- MapStruct para mapeamento automático
- Métodos explícitos quando necessário

**Exemplo**:
```java
@Mapper(componentModel = "spring")
public interface SeedMapper {
    SeedResponse toResponse(Seed seed);
    Seed toDomain(SeedRequest request);
    // MapStruct gera implementação automaticamente
}

@Mapper(componentModel = "spring")
public interface SeedPersistenceMapper {
    SeedEntity toEntity(Seed domain);
    Seed toDomain(SeedEntity entity);
}
```

---

### 3.8 Entidades JPA (Persistence)

**Localização**: `infrastructure/persistence/entity/*Entity.java`

**Características**:
- Anotações `@Entity`, `@Table`
- Anotações Lombok (`@Getter`, `@AllArgsConstructor`, etc.)
- Chaves primárias `@GeneratedValue(strategy = GenerationType.IDENTITY)`
- Relacionamentos com lazy loading
- Validações com `@NotBlank`, `@Column`, etc.

**Exemplo**:
```java
@Entity
@Table(name = "tb_seeds")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SeedEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(length = 100)
    private String species;
    
    @NotBlank
    @Column(length = 100)
    private String cultivar;
    
    @Column
    private boolean isProtected;
    
    @Column
    private Boolean active;
    
    @Column
    private String normalizedSpecies;
    
    @Column
    private String normalizedCultivar;
}
```

---

### 3.9 Specifications (JPA Criteria API)

**Localização**: `infrastructure/persistence/specification/*Specifications.java`

**Características**:
- Métodos estáticos que retornam `Specification<Entity>`
- Utilizados em Repositories para queries dinâmicas
- Reutilizáveis em múltiplos repositórios

**Exemplo**:
```java
public class SeedSpecifications {
    
    public static Specification<SeedEntity> hasProtected(Boolean isProtected) {
        return (root, query, cb) -> {
            if (isProtected == null) return cb.conjunction();
            return cb.equal(root.get("isProtected"), isProtected);
        };
    }
    
    public static Specification<SeedEntity> isActive() {
        return (root, query, cb) -> cb.equal(root.get("active"), true);
    }
}
```

---

## 4. Fluxo de Requisição

```
1. Cliente faz requisição HTTP
    ↓
2. Controller recebe e valida DTO (validações JSR-303)
    ↓
3. Controller cria Domain Model usando factory
    ↓
4. Controller invoca Use Case passando Domain Model
    ↓
5. Use Case executa lógica de negócio:
   - Valida regras
   - Chama Repository (Port)
   - Pode lançar exceções de domínio
    ↓
6. Repository Adapter (implementação):
   - Converte Domain para Entity
   - Chama JPA Repository
   - Converte Entity de volta para Domain
    ↓
7. Use Case retorna Domain Model para Controller
    ↓
8. Controller mapeia Domain para DTO Response
    ↓
9. Exception Handler (se houver erro):
   - Captura exceções de domínio
   - Converte para resposta HTTP apropriada
    ↓
10. Resposta HTTP retorna ao cliente
```

---

## 5. Segurança

### 5.1 Autenticação (JWT)

- Implementação: `api/security/JwtTokenProvider.java`
- Filtro: `api/security/JwtAuthenticationFilter.java`
- Configuração: `infrastructure/config/SecurityConfig.java`

### 5.2 Autorização (Roles)

**Roles Disponíveis**:
- `ADMIN`: Acesso total
- `STANDARD`: Acesso padrão
- `READ_ONLY`: Apenas leitura

**Uso em Controllers**:
```java
@PreAuthorize("hasRole('ADMIN')")          // Apenas ADMIN
@PreAuthorize("hasAnyRole('ADMIN', 'STANDARD')") // ADMIN ou STANDARD
@PreAuthorize("hasAnyRole('ADMIN', 'STANDARD', 'READ_ONLY')") // Todos
```

### 5.3 Proteção contra XSS

- Filtro: `infrastructure/web/XssFilter.java`
- Sanitização automática de entrada e saída
- Padrão: `ResponseSanitizerAdvice.java`

### 5.4 Multi-tenant (Isolamento de Tenants)

IMPORTANTE: esta seção contém regras obrigatórias para evitar vazamento de dados entre empresas.

Por que isso é crítico?
- Em sistemas SaaS multi-tenant um bug na resolução do tenant pode causar vazamento de dados entre empresas — isto é inaceitável.

Princípios essenciais:
- Nunca confiar em companyId / tenantId vindo do cliente.
- O tenant deve sempre ser resolvido a partir do contexto de autenticação (JWT) e aplicado no backend.
- Aplicar defesa em profundidade: preencher TenantContext no filtro de autenticação, validar/usar no UseCase e filtrar nas queries de persistência.

Regras obrigatórias:
1. DTOs nunca devem confiar ou conter companyId enviado pelo cliente (remover do body).
2. O JWT (quando emitido) deve conter o claim `tenantId` (company id) e opcionalmente `tenantCode`.
3. Um filtro de autenticação (ex: `JwtAuthenticationFilter`) deve extrair o tenantId do token e setar `TenantContext.setTenantId(tenantId)`.
4. Sempre limpar o `TenantContext` no final do request (`TenantContext.clear()`).
5. UseCases sempre resolvem o tenantId via `CurrentUser.getCompanyId()` (que delega para `TenantContext`).
6. Super-admins podem ter permissão especial para operar em outro tenant, mas isso deve ser validado no UseCase — nunca no Controller.
7. Repositórios e consultas JPA devem filtrar explicitamente por `company_id` nas cláusulas WHERE (e ter FK + índices no DB).

Onde adicionar o suporte (padrão recomendado):
- `application/support/TenantContext` (ThreadLocal)
- `application/support/CurrentUser` (helper que lê TenantContext e verifica roles)
- `api/security/JwtAuthenticationFilter` (seta o TenantContext a partir do token JWT)
- `application/usecase/*` (UseCases resolvem tenantId e criam Aggregates com tenantId)
- `infrastructure/persistence/*` (queries sempre incluem company_id)

Exemplos (resumo):

TenantContext (application/support/TenantContext.java):

```java
@Component
public class TenantContext {
    private static final ThreadLocal<Long> CURRENT_TENANT = new ThreadLocal<>();
    public static void setTenantId(Long tenantId) { CURRENT_TENANT.set(tenantId); }
    public static Long getTenantId() { return CURRENT_TENANT.get(); }
    public static void clear() { CURRENT_TENANT.remove(); }
}
```

CurrentUser helper (application/support/CurrentUser.java):

```java
public class CurrentUser {
    public static Long getCompanyId() { return TenantContext.getTenantId(); }
    public static boolean isSuperAdmin() { /* checar authorities no SecurityContextHolder */ }
}
```

JwtAuthenticationFilter (após validar token):

```java
Long tenantId = jwtTokenProvider.getTenantIdFromToken(token);
if (tenantId != null) TenantContext.setTenantId(tenantId);
// ... no finally: TenantContext.clear();
```

UseCase (exemplo de RegisterTechnicalResponsibleUseCase):

```java
public TechnicalResponsible execute(TechnicalResponsibleRequest request, Long optionalCompanyId) {
    Long companyId;
    if (CurrentUser.isSuperAdmin() && optionalCompanyId != null) {
        companyId = optionalCompanyId;
    } else {
        companyId = CurrentUser.getCompanyId();
    }

    TechnicalResponsible tr = TechnicalResponsible.newTechnicalResponsible(companyId, ...);
    return repository.save(tr);
}
```

Controller: NÃO deve aceitar `companyId` no corpo do request. Se permitir override para SUPER_ADMIN, use um `@RequestParam` opcional (controlado e validado no UseCase).

Banco de dados:
- Adicionar FK `company_id` e índices. Todas as queries críticas devem usar `WHERE company_id = :tenantId`.

Checklist de segurança multi-tenant (obrigatório ao entregar features):
- [ ] DTOs sem companyId no body
- [ ] JWT tem claim tenantId
- [ ] JwtAuthenticationFilter setando TenantContext
- [ ] UseCases usam CurrentUser/TenantContext e aplicam super-admin override apenas quando seguro
- [ ] Repositório e consultas JPA com filtro por company_id
- [ ] Migrations garantem FK + índice por company_id

---

## 6. Validação e Tratamento de Erros

### 6.1 Validação em Múltiplas Camadas

1. **API Layer**: DTOs com anotações JSR-303 (`@NotBlank`, `@Pattern`, `@Size`)
2. **Domain Layer**: Validações estáticas em factories e métodos de negócio
3. **Application Layer**: Regras de negócio complexas

### 6.2 Tratamento de Exceções

**Global Exception Handler**: `api/exception/GlobalExceptionHandler.java`

Mapeamento:
- `ValidationException` → 400 BAD_REQUEST
- `BusinessException` → 409 CONFLICT
- `ResourceNotFoundException` → 404 NOT_FOUND
- `UnauthorizedException` → 401 UNAUTHORIZED
- `Exception` → 500 INTERNAL_SERVER_ERROR

Observação importante sobre mensagens de erro:

Todas as mensagens de erro exibidas pela aplicação — incluindo mensagens de exceção de domínio, mensagens de validação retornadas pela API e mensagens destinadas ao usuário final — devem ser escritas em Português Brasileiro (pt-BR). Essa regra se aplica a mensagens geradas no backend e a qualquer texto de erro que seja persistido ou retornado em respostas HTTP.

Motivação e recomendações:
- Uniformizar a linguagem melhora a experiência do usuário e facilita o suporte e a triagem de incidentes.
- Evitar exposição de dados sensíveis nas mensagens (ex.: não incluir stacks, tokens, senhas ou dados pessoais completos).
- Para mensagens técnicas (logs, traces) que não serão expostas ao usuário, mantenha formato e conteúdo apropriados para depuração, mas prefira também registrar uma versão em português quando relevante para suporte local.
- Centralizar mensagens (ex.: arquivos de constantes ou resource bundles) facilita traduções futuras e testes.

---

## 7. Paginação e Ordenação

**Padrão usado**: Spring Data `Pageable`

**Exemplos de Uso em Controllers**:
```java
@GetMapping
public ResponseEntity<Page<SeedResponse>> listAll(
    @ParameterObject Pageable pageable,
    @RequestParam(value = "protected", required = false) Boolean isProtected) {
    
    Page<Seed> page = findPagedSeedsUseCase.execute(isProtected, pageable);
    return ResponseEntity.ok(page.map(mapper::toResponse));
}
```

**Query Parameters**:
- `page`: Número da página (começa em 0)
- `size`: Quantidade de itens por página
- `sort`: Ordenação (formato: `campo,direção` ex: `id,asc`)

**Exemplo de requisição**:
```
GET /seeds?page=0&size=10&sort=id,asc&protected=true
```

---

## 8. Migrations de Banco de Dados

**Ferramenta**: Flyway

**Localização**: `src/main/resources/db/migration/`

**Nomenclatura**: `V{versão}__{descrição}.sql`

**Exemplo**: `V1__Create_seeds_table.sql`

---

## 9. Configurações (application.yaml / application.properties)

**Arquivo Principal**: `src/main/resources/application.yaml`

**Profiles**:
- `dev`: Desenvolvimento
- `test`: Testes
- `prod`: Produção

---

## 10. Dependências Principais

- **Spring Boot**: 3.5.0
- **Java**: 21
- **PostgreSQL**: Banco de dados
- **JPA/Hibernate**: ORM
- **Flyway**: Versionamento de DB
- **Lombok**: Redução de boilerplate
- **MapStruct**: Mapeamento de objetos
- **JWT (JJWT)**: Autenticação
- **SpringDoc OpenAPI**: Documentação automática (Swagger)
- **Spring Security**: Autenticação e autorização

---

## 11. Convenções de Nomenclatura

### 11.1 Pacotes
- Minúsculos, sem underscore
- Organizados por camada (api, application, domain, infrastructure)
- Subpackages por domínio funcional (seed, lot, invoice, etc)

### 11.2 Classes
- PascalCase
- Sufixos específicos:
  - `*Controller`: Controllers REST
  - `*UseCase`: Casos de uso
  - `*Mapper`: Mappers
  - `*Repository`: Interfaces de repositório
  - `*RepositoryAdapter`: Implementações de repositório
  - `*Entity`: Entidades JPA
  - `*Request`: DTOs de entrada
  - `*Response`: DTOs de saída
  - `*Service`: Serviços de domínio
  - `*Exception`: Exceções personalizadas
  - `*Specifications`: Specifications JPA

### 11.3 Métodos
- camelCase
- Nomes descritivos (get/set para properties)
- Métodos factory: `newXxx()`, `restore()`
- Casos de uso: `execute()`

### 11.4 Variáveis
- camelCase
- Nomes significativos
- Não usar single-letter (exceto em loops)

### 11.5 Constantes
- UPPER_SNAKE_CASE
- `private static final`

---

## 12. Melhores Práticas

### 12.1 Injeção de Dependências
- Usar Constructor Injection (`@RequiredArgsConstructor`)
- Nunca usar `@Autowired` em fields
- Facilita testes unitários

### 12.2 Immutabilidade
- Domain models usam `@Getter` apenas
- Evitar setters de negócio
- Métodos explícitos para mudanças de estado

### 12.3 Comments e Documentação
- Apenas para lógica complexa
- Métodos públicos documentados (Javadoc)
- DTOs documentados com `@Schema`

### 12.4 Testes
- Testes unitários para Use Cases
- Testes de integração para Controllers
- Use `@WebMvcTest` para controllers
- Use `@DataJpaTest` para repositories

### 12.5 Commits
- Seguir **Conventional Commits**
- Tipos: `feat`, `fix`, `refactor`, `chore`, `test`, `docs`, `perf`, `build`, `ci`
- Exemplo: `feat(seed): add new seed creation endpoint`

---

## 13. Exemplo Completo de Novo Recurso

Suponha que precisa criar um novo recurso "Fertilizer" (Adubo):

### 13.1 Domain Model
```java
// domain/model/Fertilizer.java
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fertilizer {
    private Long id;
    private String name;
    private String composition;
    private Boolean active = true;
    
    public static Fertilizer newFertilizer(String name, String composition) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Nome é obrigatório");
        }
        Fertilizer f = new Fertilizer();
        f.id = null;
        f.name = name;
        f.composition = composition;
        f.active = true;
        return f;
    }
}
```

### 13.2 Repository Interface
```java
// domain/repository/FertilizerRepository.java
public interface FertilizerRepository {
    Fertilizer save(Fertilizer fertilizer);
    Optional<Fertilizer> findById(Long id);
    Page<Fertilizer> findAll(Pageable pageable);
}
```

### 13.3 JPA Entity
```java
// infrastructure/persistence/entity/FertilizerEntity.java
@Entity
@Table(name = "tb_fertilizers")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FertilizerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(length = 100)
    private String name;
    
    @Column(length = 500)
    private String composition;
    
    @Column
    private Boolean active;
}
```

### 13.4 Repository Adapter
```java
// infrastructure/persistence/adapter/FertilizerRepositoryAdapter.java
@Component
public class FertilizerRepositoryAdapter implements FertilizerRepository {
    private final JpaFertilizerRepository jpaRepository;
    private final FertilizerPersistenceMapper mapper;
    
    public FertilizerRepositoryAdapter(JpaFertilizerRepository jpaRepository, 
                                      FertilizerPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public Fertilizer save(Fertilizer fertilizer) {
        FertilizerEntity entity = mapper.toEntity(fertilizer);
        return mapper.toDomain(jpaRepository.save(entity));
    }
    
    @Override
    public Optional<Fertilizer> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
    
    @Override
    public Page<Fertilizer> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable).map(mapper::toDomain);
    }
}
```

### 13.5 Use Cases
```java
// application/usecase/fertilizer/RegisterFertilizerUseCase.java
@Component
@RequiredArgsConstructor
public class RegisterFertilizerUseCase {
    private final FertilizerRepository fertilizerRepository;
    
    public Fertilizer execute(Fertilizer fertilizer) {
        return fertilizerRepository.save(fertilizer);
    }
}
```

### 13.6 DTOs
```java
// api/dto/request/FertilizerRequest.java
@Setter
@Getter
public class FertilizerRequest {
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100)
    private String name;
    
    @Size(max = 500)
    private String composition;
}

// api/dto/response/FertilizerResponse.java
@Getter
@Setter
public class FertilizerResponse {
    private Long id;
    private String name;
    private String composition;
}
```

### 13.7 Mapper
```java
// api/mapper/FertilizerMapper.java
@Mapper(componentModel = "spring")
public interface FertilizerMapper {
    FertilizerResponse toResponse(Fertilizer fertilizer);
    Fertilizer toDomain(FertilizerRequest request);
}
```

### 13.8 Controller
```java
// api/controller/FertilizerController.java
@RestController
@RequiredArgsConstructor
@RequestMapping("/fertilizers")
@Tag(name = "Fertilizers", description = "Operações de adubos")
public class FertilizerController {
    
    private final FertilizerMapper mapper;
    private final RegisterFertilizerUseCase registerFertilizerUseCase;
    private final FindPagedFertilizersUseCase findPagedUseCase;
    private final FindFertilizerByIdUseCase findByIdUseCase;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FertilizerResponse> register(@Valid @RequestBody FertilizerRequest request) {
        Fertilizer fertilizer = Fertilizer.newFertilizer(request.getName(), request.getComposition());
        Fertilizer saved = registerFertilizerUseCase.execute(fertilizer);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(saved));
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD', 'READ_ONLY')")
    public ResponseEntity<Page<FertilizerResponse>> listAll(@ParameterObject Pageable pageable) {
        Page<Fertilizer> page = findPagedUseCase.execute(pageable);
        return ResponseEntity.ok(page.map(mapper::toResponse));
    }
}
```

---

## 14. Checklist para Novas Features

- [ ] Domain Model criado com factory e restore
- [ ] Repository interface criada no domain
- [ ] JPA Entity criada na infraestrutura
- [ ] Repository Adapter implementado
- [ ] Persistence Mapper criado
- [ ] DTOs Request e Response criados
- [ ] API Mapper criado
- [ ] Use Cases criados (Register, Find, Update, Delete)
- [ ] Controller criado com endpoints REST
- [ ] Validações adicionadas (DTOs e Domain)
- [ ] OpenAPI documentation adicionada (@Tag, @Operation)
- [ ] Security rules adicionadas (@PreAuthorize)
- [ ] Tests unitários criados
- [ ] Migrations de DB criadas (Flyway)
- [ ] Exception Handlers atualizados se necessário
- [ ] Commit com mensagem Conventional Commits

---

## 15. Referências

- **Clean Architecture**: Robert Martin (Uncle Bob)
- **Domain-Driven Design**: Eric Evans
- **Hexagonal Architecture**: Alistair Cockburn
- **Spring Boot Documentation**: https://spring.io/projects/spring-boot
- **MapStruct**: https://mapstruct.org/
- **Flyway**: https://flywaydb.org/

---

**Versão**: 1.0
**Data**: 2024
**Mantido por**: Time de Desenvolvimento WestFlow
