package com.example.task.management.system.service;

import com.example.task.management.system.exception.PasswordIncorrect;
import com.example.task.management.system.exception.PasswordOrEmailIncorrect;
import com.example.task.management.system.model.EmployeeEntity;
import com.example.task.management.system.model.request.PasswordUpdateRequest;
import com.example.task.management.system.model.request.SignInRequest;
import com.example.task.management.system.model.request.SignUpRequest;
import com.example.task.management.system.model.response.EmployeeResponse;
import com.example.task.management.system.model.response.JwtAuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements  AuthenticationService {
    private final EmployeeService employeeService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest signUpRequest) {
        String password = passwordEncoder.encode(signUpRequest.getPassword());

        EmployeeEntity employeeEntity = employeeService.createEmployee(signUpRequest, password);

        var jwt = jwtService.generateToken(employeeEntity);
        return new JwtAuthenticationResponse(jwt);
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInRequest.getEmail(),
                    signInRequest.getPassword()
            ));
        } catch (BadCredentialsException badCredentialsException) {
            throw new PasswordOrEmailIncorrect();
        }

        UserDetails userDetails = employeeService
                .userDetailsService()
                .loadUserByUsername(signInRequest.getEmail());

        String jwt = jwtService.generateToken(userDetails);
        return new JwtAuthenticationResponse(jwt);
    }

    @Override
    public EmployeeResponse updatePassword(PasswordUpdateRequest passwordUpdateRequest) {
        if (passwordEncoder.matches(passwordUpdateRequest.getOldPassword(), employeeService.getCurrentEmployeePassword())) {
            String newPassword = passwordEncoder.encode(passwordUpdateRequest.getNewPassword());

            return employeeService.updatePassword(newPassword);
        } else {
            throw new PasswordIncorrect();
        }
    }
}
