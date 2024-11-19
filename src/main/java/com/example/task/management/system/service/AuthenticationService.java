package com.example.task.management.system.service;

import com_example_task_management_system_model.JwtAuthenticationResponse;
import com_example_task_management_system_model.PasswordUpdateRequest;
import com_example_task_management_system_model.SignInRequest;
import com_example_task_management_system_model.SignUpRequest;
import com_example_task_management_system_model.EmployeeResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
    EmployeeResponse updatePassword(PasswordUpdateRequest passwordUpdateRequest);
}
