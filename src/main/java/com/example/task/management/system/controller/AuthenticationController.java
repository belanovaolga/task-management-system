package com.example.task.management.system.controller;

import com.example.task.management.system.service.AuthenticationService;
import com_example_task_management_system_api.AuthenticationApi;
import com_example_task_management_system_model.JwtAuthenticationResponse;
import com_example_task_management_system_model.PasswordUpdateRequest;
import com_example_task_management_system_model.SignInRequest;
import com_example_task_management_system_model.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {
    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<JwtAuthenticationResponse> signUp(SignUpRequest signUpRequest) {
        authenticationService.signUp(signUpRequest);
        return AuthenticationApi.super.signUp(signUpRequest);
    }

    @Override
    public ResponseEntity<JwtAuthenticationResponse> signIn(SignInRequest signInRequest) {
        authenticationService.signIn(signInRequest);
        return AuthenticationApi.super.signIn(signInRequest);
    }

    @Override
    public ResponseEntity<Void> updatePassword(PasswordUpdateRequest passwordUpdateRequest) {
        authenticationService.updatePassword(passwordUpdateRequest);
        return AuthenticationApi.super.updatePassword(passwordUpdateRequest);
    }
}
