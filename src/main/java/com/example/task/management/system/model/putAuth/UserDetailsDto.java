package com.example.task.management.system.model.putAuth;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDetailsDto extends AbstractAuthenticationToken {
    private final String token;
    private final Claims claims;

    public UserDetailsDto(Collection<? extends GrantedAuthority> authorities, String token, Claims claims) {
        super(authorities);
        this.token = token;
        this.claims = claims;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return claims;
    }

    public Long getId() {
        return claims.get("id", Long.class);
    }
}
