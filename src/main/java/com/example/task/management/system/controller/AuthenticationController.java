package com.example.task.management.system.controller;

import com.example.task.management.system.api.AuthApi;
import com.example.task.management.system.model.JwtAuthenticationResponse;
import com.example.task.management.system.model.PasswordUpdateRequest;
import com.example.task.management.system.model.SignInRequest;
import com.example.task.management.system.model.SignUpRequest;
import com.example.task.management.system.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthApi {
    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<JwtAuthenticationResponse> signUp(SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @Override
    public ResponseEntity<JwtAuthenticationResponse> signIn(SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

    @Override
    public ResponseEntity<Void> updatePassword(PasswordUpdateRequest passwordUpdateRequest) {
        authenticationService.updatePassword(passwordUpdateRequest);
        return ResponseEntity.noContent().build();
    }
}
