package com.wolley.tech.wmessenger.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {


    @Value("${security.api.key}")
    private String configuredApiKey;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String apiKey = (String) authentication.getCredentials();

        if (configuredApiKey.equals(apiKey)) {
            return new UsernamePasswordAuthenticationToken(
                    apiKey,
                    null,
                    AuthorityUtils.NO_AUTHORITIES
            );
        }

        throw new BadCredentialsException("Invalid API Key");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthentication.class.isAssignableFrom(authentication);
    }
}
