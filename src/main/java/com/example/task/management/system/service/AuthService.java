package com.example.task.management.system.service;

import com.example.task.management.system.model.putAuth.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;

    public UserDetailsDto getCurrentEmployeesId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = (String) authentication.getPrincipal();

        return new UserDetailsDto(authentication.getAuthorities(), token, jwtService.getExtractAllClaims(token));
    }
}
