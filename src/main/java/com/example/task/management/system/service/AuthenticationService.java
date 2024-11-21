package com.example.task.management.system.service;

import com.example.task.management.system.model.JwtAuthenticationResponse;
import com.example.task.management.system.model.PasswordUpdateRequest;
import com.example.task.management.system.model.SignInRequest;
import com.example.task.management.system.model.SignUpRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
    void updatePassword(PasswordUpdateRequest passwordUpdateRequest);
}
