# 📘 Seeds Manager API — Lot Use Cases

Lições Aprendidas, Decisões Arquiteturais e Regras de Ouro (Guia para AI Agent)

---

## Objetivo deste documento

Garantir que futuras refatorações e novos casos de uso sigam corretamente o modelo arquitetural do projeto, evitando regressões graves de domínio, como as identificadas durante a refatoração do [Lot](cci:2://file:///C:/Projetos/WestFlow/seeds-manager-api/src/main/java/com/westflow/seeds_manager_api/domain/model/Lot.java:16:0-221:1).

---

## 1. Visão Arquitetural Consolidada

### 1.1 Arquitetura adotada

O projeto segue uma DDD pragmática, com separação clara entre:

- **Application Layer (Use Cases)**
- **Domain Layer (Entidades + Serviços de Domínio)**
- **Infrastructure Layer (Persistence, Mappers, Repositories)**

📌 **Decisão importante**:

- Services antigos (`Service` / `ServiceImpl`) estão sendo substituídos por **UseCases explícitos**, mantendo serviços de domínio apenas quando existe **regra de negócio reutilizável**.

---

## 2. Decisões Arquiteturais Importantes (aprendidas na prática)

### 2.1 Lote NÃO consome saldo

🚨 **Erro grave identificado e corrigido**

- ❌ Nunca descontar `Lot.balance` na criação ou atualização do lote.
- ✅ O `Lot.balance` só é alterado em:
    - `LotReservation`
    - `LotWithdrawal`

📌 **Motivo de domínio**:

- O lote representa **capacidade disponível**, não consumo real.
- Consumo real ocorre apenas quando há **reserva** ou **saída física**.

### 2.2 Quem consome saldo é a `Invoice`

Na criação ou atualização de lote:

- O relacionamento [Lot](cci:2://file:///C:/Projetos/WestFlow/seeds-manager-api/src/main/java/com/westflow/seeds_manager_api/domain/model/Lot.java:16:0-221:1) ↔ `Invoice` é feito via `LotInvoice`.
- Quem perde saldo é a **Invoice**, nunca o [Lot](cci:2://file:///C:/Projetos/WestFlow/seeds-manager-api/src/main/java/com/westflow/seeds_manager_api/domain/model/Lot.java:16:0-221:1).

Resumo:

- **Lot** → estrutura / agregador.
- **LotInvoice** → relação + quantidades.
- **Invoice** → origem do estoque (saldo real).

### 2.3 `LotInvoice` é uma entidade de domínio (não DTO técnico)

📌 O nome já define a intenção:

- `LotInvoice` = relação entre [Lot](cci:2://file:///C:/Projetos/WestFlow/seeds-manager-api/src/main/java/com/westflow/seeds_manager_api/domain/model/Lot.java:16:0-221:1) e `Invoice`.

Responsabilidades:

- Guardar:
    - quantidade no mundo do lote
    - quantidade equivalente no mundo da nota
- Registrar histórico de alocação.
- Nunca conter lógica de persistência ou de saldo.

---

## 3. Regras de Ouro (para qualquer AI Agent)

### 🥇 Regra de Ouro #1 — Side effects nunca no `DomainService`

`LotDomainService`:

- ✅ **Pode**: validar, calcular.
- ❌ **Não pode**: persistir, alterar saldo.

Side effects (saldo, delete, save) **sempre** no **UseCase** ou **Application Service**.

---

### 🥈 Regra de Ouro #2 — Criação e Update devem ser simétricos

Se no **create** você:

- consome saldo da `Invoice`

No **update**, você **obrigatoriamente** deve:

- restaurar saldo antigo
- recalcular
- consumir novamente

📌 Nunca pode existir:

- lógica diferente entre [CreateLotUseCase](cci:2://file:///C:/Projetos/WestFlow/seeds-manager-api/src/main/java/com/westflow/seeds_manager_api/application/usecase/lot/CreateLotUseCase.java:0:0-19:84) e [UpdateLotUseCase](cci:2://file:///C:/Projetos/WestFlow/seeds-manager-api/src/main/java/com/westflow/seeds_manager_api/application/usecase/lot/UpdateLotUseCase.java:18:0-20:31).

---

### 🥉 Regra de Ouro #3 — Toda alteração de saldo deve ser explícita

Nunca faça:

- `invoice.setBalance(...)`

Sempre:

- `invoice.withUpdatedBalance(amount);`   // consome
- `invoice.restoreBalance(amount);`       // devolve

📌 Isso garante:

- intenção clara
- invariantes protegidas
- leitura fácil para humanos e IA

---

## 4. Regras de Negócio Consolidadas (Lot)

### 4.1 Nota fiscal é sempre obrigatória no Lote

- ❌ Não existe [Lot](cci:2://file:///C:/Projetos/WestFlow/seeds-manager-api/src/main/java/com/westflow/seeds_manager_api/domain/model/Lot.java:16:0-221:1) sem `LotInvoice`.
- ❌ Não existe [Lot](cci:2://file:///C:/Projetos/WestFlow/seeds-manager-api/src/main/java/com/westflow/seeds_manager_api/domain/model/Lot.java:16:0-221:1) com `quantityTotal` sem allocation.

Essa regra **não vai mudar**.

### 4.2 Update de Lote é condicionalmente proibido

Um [Lot](cci:2://file:///C:/Projetos/WestFlow/seeds-manager-api/src/main/java/com/westflow/seeds_manager_api/domain/model/Lot.java:16:0-221:1) **não pode ser alterado** se existir:

- `LotWithdrawal`
- `LotReservation`

📌 Essa validação acontece:

- na **Application Layer**
- antes de qualquer alteração de estado.

### 4.3 Pureza é regra de domínio, não de infraestrutura

Conversões de quantidade:

- são feitas no **domínio**
- com regras **explícitas**

Infraestrutura **nunca** recalcula pureza.

---

## 5. Responsabilidades por Camada (Guia para IA)

### 5.1 Controller

- Recebe **DTO in / DTO out**.
- Nenhuma regra de negócio.
- Nenhum acesso direto a repositório.

### 5.2 UseCase (Application Layer)

Responsável por:

- fluxo transacional
- orquestração
- ordem correta dos passos
- garantir simetria entre **create / update / delete**

**Padrão mental:**

> `load → validate → create/update domain → persist → side effects → response`

### 5.3 Domain Service (`LotDomainService`)

Pode:

- validar
- calcular
- criar objetos de domínio

Não pode:

- salvar
- deletar
- alterar saldo
- acessar repositório

### 5.4 Infrastructure / Mapper

- Mapper não cria domínio inválido.
- Quando construtor é protegido:
    - usar [restore(...)](cci:1://file:///C:/Projetos/WestFlow/seeds-manager-api/src/main/java/com/westflow/seeds_manager_api/domain/model/Lot.java:155:4-209:5) ou
    - `@ObjectFactory`.
- Nunca violar invariantes do domínio no mapper.

---

## 6. Anti-Padrões Identificados (NUNCA repetir)

- ❌ Descontar saldo do lote na criação.
- ❌ `DomainService` salvando entidade.
- ❌ Mapper chamando `new Entity()` ignorando invariantes.
- ❌ Update com regras diferentes do Create.
- ❌ `Invoice.balance` sendo alterado fora de métodos de domínio.
- ❌ Regras de negócio espalhadas em Mapper ou Repository.

---

## 7. Checklist Final para AI Agent (antes de codar)

Antes de gerar ou refatorar código, o agente deve responder internamente:

1. **Estou alterando saldo?**
    - De quem? `Invoice` ou [Lot](cci:2://file:///C:/Projetos/WestFlow/seeds-manager-api/src/main/java/com/westflow/seeds_manager_api/domain/model/Lot.java:16:0-221:1)?

2. **Isso é validação, cálculo ou efeito colateral?**
    - Validação/cálculo → **Domínio**
    - Efeito colateral → **UseCase**

3. **Create e Update estão simétricos?**

4. **Alguma regra de domínio está no Mapper ou Controller?**
    - Se sim → está errado.

---

## 8. Uso deste documento

Este documento serve como guia rápido para humanos e agentes de IA ao trabalhar com casos de uso de [Lot](cci:2://file:///C:/Projetos/WestFlow/seeds-manager-api/src/main/java/com/westflow/seeds_manager_api/domain/model/Lot.java:16:0-221:1), evitando regressões de domínio e mantendo as decisões arquiteturais intactas.