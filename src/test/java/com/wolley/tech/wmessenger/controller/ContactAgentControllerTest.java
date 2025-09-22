package com.wolley.tech.wmessenger.controller;

import com.wolley.tech.wmessenger.model.ContactAgent;
import com.wolley.tech.wmessenger.repository.ContactAgentRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


public class ContactAgentControllerTest extends BaseControllerTest {

    @Autowired
    private ContactAgentRepository repository;

    @BeforeEach
    void cleanDatabase() {
        repository.deleteAllInBatch();
    }


    @Test
    void shouldSaveContactAgents() {
        given()
                .contentType(ContentType.JSON)
                .header(HEADER_API_KEY, apiKey)
                .body("""
                        {
                           "name": "Wolley",
                           "phoneNumber": "11977777777"
                        }
                        """)
                .when()
                .post("/contact-agents")
                .then()
                .statusCode(201)
                .contentType("application/json")
                .body("name", is("Wolley"))
                .body("id", notNullValue())
                .body("agentKey", notNullValue())
                .body("phoneNumber", is("11977777777"));
    }


    @Test
    void get_when_phoneNumber_isNotFound_then_return404() {
        given()
                .contentType(ContentType.JSON)
                .header(HEADER_API_KEY, apiKey)
                .when()
                .get(String.format("/contact-agents/phoneNumber/%s", 1))
                .then()
                .statusCode(404)
                .contentType("application/problem+json")
                .body("detail", notNullValue());
    }

    @Test
    void shouldGetByPhoneNumber() {
        var agent = new ContactAgent();
        agent.setName("Name test");
        agent.setAgentKey(UUID.randomUUID());
        agent.setPhoneNumber("1199999999");
        var saved = repository.save(agent);


        given()
                .contentType(ContentType.JSON)
                .header(HEADER_API_KEY, apiKey)
                .when()
                .get(String.format("/contact-agents/phoneNumber/%s", saved.getPhoneNumber()))
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("name", is("Name test"))
                .body("id", notNullValue())
                .body("agentKey", notNullValue())
                .body("phoneNumber", is("1199999999"));


    }


}
