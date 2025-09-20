package com.wolley.tech.wmessenger.testcontainer;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;


@TestConfiguration(proxyBeanMethods = false)
public class TestcontainerConfiguration {


    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>("postgres:16-alpine")
                .withDatabaseName("wmessenger")
                .withUsername("appuser")
                .withPassword("wmessenger")
                .withInitScript("schema.sql");
    }


}
