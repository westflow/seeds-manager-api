package com.westflow.seeds_manager_api.api.controller;

import com.westflow.seeds_manager_api.api.dto.request.LoginRequest;
import com.westflow.seeds_manager_api.api.dto.request.ResetPasswordRequest;
import com.westflow.seeds_manager_api.api.dto.response.LoginResponse;
import com.westflow.seeds_manager_api.application.usecase.auth.LoginUseCase;
import com.westflow.seeds_manager_api.application.usecase.auth.RequestPasswordResetUseCase;
import com.westflow.seeds_manager_api.application.usecase.auth.ResetPasswordUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Operações de autenticação")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RequestPasswordResetUseCase requestPasswordResetUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;

    @Operation(
            summary = "Autentica um usuário",
            description = "Recebe credenciais e retorna um token JWT válido",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login bem-sucedido"),
                    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = loginUseCase.execute(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @Operation(
            summary = "Inicia processo de recuperação de senha",
            description = "Gera um token de reset e envia um email com o link de recuperação",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Se existir, email de recuperação será enviado"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestParam String email) {
        requestPasswordResetUseCase.execute(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Conclui o reset de senha",
            description = "Valida o token de reset e define uma nova senha para o usuário",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha redefinida com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Token inválido/expirado ou dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        resetPasswordUseCase.execute(request.getToken(), request.getNewPassword());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
