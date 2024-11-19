package com.example.task.management.system.controller;

import com.example.task.management.system.exception.EmployeeAlreadyExists;
import com.example.task.management.system.exception.PasswordIncorrect;
import com.example.task.management.system.model.request.PasswordUpdateRequest;
import com.example.task.management.system.model.request.SignInRequest;
import com.example.task.management.system.model.request.SignUpRequest;
import com.example.task.management.system.model.response.JwtAuthenticationResponse;
import com.example.task.management.system.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Контроллер аутентификации", description = "Позволяет авторизоваться в системе")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    @Operation(
            summary = "Регистрация пользователя",
            description = "Позволяет зарегистрировать пользователя"
    )
    public ResponseEntity<JwtAuthenticationResponse> signUp(
            @RequestBody @Valid SignUpRequest signUpRequest
    ) throws EmployeeAlreadyExists {
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/sign-in")
    @Operation(
            summary = "Авторизация пользователя",
            description = "Позволяет пользователю авторизоваться в системе"
    )
    public ResponseEntity<JwtAuthenticationResponse> signIn(
            @RequestBody @Valid SignInRequest signInRequest
    ) throws PasswordIncorrect {
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

    @PutMapping
//    @PreAuthorize("hasAnyRole('ROLE_admin', 'ROLE_user')")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Изменение пароля",
            description = "Позволяет пользователю сменить пароль"
    )
    public void updatePassword(
            @RequestBody PasswordUpdateRequest passwordUpdateRequest
    ) {
        ResponseEntity.ok(authenticationService.updatePassword(passwordUpdateRequest));
    }
}
