package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.config.CurrentUser;
import com.westflow.seeds_manager_api.api.dto.request.UserAdminUpdateRequest;
import com.westflow.seeds_manager_api.api.dto.request.UserChangePasswordRequest;
import com.westflow.seeds_manager_api.api.dto.request.UserCreateRequest;
import com.westflow.seeds_manager_api.api.dto.request.UserProfileUpdateRequest;
import com.westflow.seeds_manager_api.api.dto.response.UserResponse;
import com.westflow.seeds_manager_api.application.usecase.user.AdminUpdateUserUseCase;
import com.westflow.seeds_manager_api.application.usecase.user.ChangeUserPasswordUseCase;
import com.westflow.seeds_manager_api.application.usecase.user.RegisterUserUseCase;
import com.westflow.seeds_manager_api.application.usecase.user.UpdateUserProfileUseCase;
import com.westflow.seeds_manager_api.api.mapper.UserMapper;
import com.westflow.seeds_manager_api.domain.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users", description = "Operações de usuários")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final UpdateUserProfileUseCase updateUserProfileUseCase;
    private final ChangeUserPasswordUseCase changeUserPasswordUseCase;
    private final AdminUpdateUserUseCase adminUpdateUserUseCase;
    private final UserMapper userMapper;

    @Operation(
            summary = "Cria um novo usuário",
            description = "Valida e registra um novo usuário no sistema",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request) {
        User user = User.newUser(
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getPosition(),
                request.getAccessLevel()
        );

        User saved = registerUserUseCase.execute(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponse(saved));
    }

    @Operation(
            summary = "Retorna o usuário logado",
            description = "Retorna as informações do usuário autenticado para a tela de perfil",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso")
            }
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@Parameter(hidden = true) @CurrentUser User currentUser) {
        return ResponseEntity.ok(userMapper.toResponse(currentUser));
    }

    @Operation(
            summary = "Atualiza o perfil do usuário logado",
            description = "Permite ao usuário alterar nome e cargo",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(
            @Parameter(hidden = true) @CurrentUser User currentUser,
            @Valid @RequestBody UserProfileUpdateRequest request
    ) {
        UserResponse response = updateUserProfileUseCase.execute(currentUser, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Altera a senha do usuário logado",
            description = "Permite ao usuário alterar sua senha informando a senha atual",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Senha atual inválida ou dados inválidos")
            }
    )
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/profile/password")
    public ResponseEntity<Void> changePassword(
            @Parameter(hidden = true) @CurrentUser User currentUser,
            @Valid @RequestBody UserChangePasswordRequest request
    ) {
        changeUserPasswordUseCase.execute(currentUser, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Atualiza um usuário (admin)",
            description = "Permite a um administrador atualizar dados de um usuário não ADMIN",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> adminUpdate(
            @PathVariable Long id,
            @Valid @RequestBody UserAdminUpdateRequest request
    ) {
        UserResponse response = adminUpdateUserUseCase.execute(id, request);
        return ResponseEntity.ok(response);
    }
}
