package com.wolley.tech.wmessenger.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class ApiKeyAuthentication extends AbstractAuthenticationToken {
    private final String apiKey;

    public ApiKeyAuthentication(String apiKey) {
        super(null);
        this.apiKey = apiKey;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return apiKey;
    }

    @Override
    public Object getPrincipal() {
        return apiKey;
    }
}
