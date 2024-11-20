package com.example.task.management.system.service;

import com.example.task.management.system.model.*;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
    EmployeeResponse updatePassword(PasswordUpdateRequest passwordUpdateRequest);
}
