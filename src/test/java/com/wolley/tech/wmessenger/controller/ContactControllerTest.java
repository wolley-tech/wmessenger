package com.wolley.tech.wmessenger.controller;


import com.wolley.tech.wmessenger.model.AgeGroup;
import com.wolley.tech.wmessenger.model.Contact;
import com.wolley.tech.wmessenger.model.GenderEnum;
import com.wolley.tech.wmessenger.repository.ContactRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactControllerTest {
    @LocalServerPort
    private Integer port;

    @Autowired
    private ContactRepository repository;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    )
            //.withExposedPorts(55432)
            .withDatabaseName("wmessenger")
            .withUsername("appuser")
            .withPassword("wmessenger")
            .withInitScript("schema.sql");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        repository.deleteAll();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void shouldSaveContacts() {


        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                           "name": "Wolley",
                           "gender": "MALE",
                           "ageGroup": "CHILD",
                           "phoneNumber": "11986064917"
                        }
                        """)
                .when()
                .post("/api/contacts")
                .then()
                .statusCode(201)
                .contentType("application/json")
                .body("name", is("Wolley"))
                .body("gender", is("MALE"))
                .body("ageGroup", is("CHILD"))
                .body("phoneNumber", is("11986064917"));

    }


    @Test
    void shouldGetContacts() {
        var contact = new Contact();
        contact.setName("Name test");
        contact.setPhoneNumber("1199999999");
        contact.setAgeGroup(AgeGroup.ADULT);
        contact.setGender(GenderEnum.FEMALE);
        repository.save(contact);

        contact = new Contact();
        contact.setName("Name test 2");
        contact.setPhoneNumber("1177777777");
        contact.setAgeGroup(AgeGroup.ADULT);
        contact.setGender(GenderEnum.FEMALE);
        repository.save(contact);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/contacts")
                .then()
                .statusCode(200)
                .body(".", hasSize(2));


    }

}
