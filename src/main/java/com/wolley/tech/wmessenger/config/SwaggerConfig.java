package com.wolley.tech.wmessenger.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Contacts API", version = "v1")
)
@SecurityScheme(
        name = "apiKeyAuth",                // nome do esquema
        type = SecuritySchemeType.APIKEY,   // tipo API KEY
        in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER, // vem no header
        paramName = "X-API-KEY"             // nome do header
)
@Configuration
public class SwaggerConfig {
}
