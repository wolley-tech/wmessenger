package com.wolley.tech.wmessenger.controller;


import com.wolley.tech.wmessenger.testcontainer.TestcontainerConfiguration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainerConfiguration.class)
public abstract class BaseControllerTest {

    @LocalServerPort
    protected Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost/api";
        RestAssured.port = port;
    }
}
