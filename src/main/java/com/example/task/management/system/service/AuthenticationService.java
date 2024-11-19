package com.example.task.management.system.service;

import com.example.task.management.system.model.request.PasswordUpdateRequest;
import com.example.task.management.system.model.request.SignInRequest;
import com.example.task.management.system.model.request.SignUpRequest;
import com.example.task.management.system.model.response.EmployeeResponse;
import com.example.task.management.system.model.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
    EmployeeResponse updatePassword(PasswordUpdateRequest passwordUpdateRequest);
}
