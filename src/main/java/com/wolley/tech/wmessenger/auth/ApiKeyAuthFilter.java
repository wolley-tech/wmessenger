package com.wolley.tech.wmessenger.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String apiKey = request.getHeader("X-API-KEY");

        if (apiKey != null) {
            ApiKeyAuthentication authRequest = new ApiKeyAuthentication(apiKey);
            SecurityContextHolder.getContext().setAuthentication(authRequest);
        }

        filterChain.doFilter(request, response);
    }
}

