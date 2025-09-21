package com.wolley.tech.wmessenger.controller;


import com.wolley.tech.wmessenger.model.AgeGroup;
import com.wolley.tech.wmessenger.model.Contact;
import com.wolley.tech.wmessenger.model.ContactAgent;
import com.wolley.tech.wmessenger.model.GenderEnum;
import com.wolley.tech.wmessenger.repository.ContactAgentRepository;
import com.wolley.tech.wmessenger.repository.ContactRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


public class ContactControllerTest extends BaseControllerTest {

    @Autowired
    private ContactRepository repository;

    @Autowired
    private ContactAgentRepository contactAgentRepository;


    @BeforeEach
    void setUpTests() {
        repository.deleteAllInBatch();
        contactAgentRepository.deleteAllInBatch();

    }

    @Test
    void shouldSaveContacts() {

        var agent = new ContactAgent();
        agent.setAgentKey(UUID.randomUUID());
        agent.setName("Name agent");
        agent.setPhoneNumber("1155555555");
        var agentSaved = contactAgentRepository.save(agent);


        given()
                .contentType(ContentType.JSON)
                .header("Content-type", ContentType.JSON)
                .header("X-agent-key", agentSaved.getAgentKey().toString())
                .body("""
                        {
                           "name": "Wolley",
                           "gender": "MALE",
                           "ageGroup": "CHILD",
                           "phoneNumber": "11977777777"
                        }
                        """)
                .when()
                .post("/contacts")
                .then()
                .statusCode(201)
                .contentType("application/json")
                .body("name", is("Wolley"))
                .body("gender", is("MALE"))
                .body("ageGroup", is("CHILD"))
                .body("phoneNumber", is("11977777777"));

    }


    @Test
    void shouldGetContacts() {
        var agent = new ContactAgent();
        agent.setAgentKey(UUID.randomUUID());
        agent.setName("Name agent");
        agent.setPhoneNumber("1155555555");
        var agentSaved = contactAgentRepository.save(agent);


        var contact = new Contact();
        contact.setName("Name test");
        contact.setPhoneNumber("1199999999");
        contact.setAgeGroup(AgeGroup.ADULT);
        contact.setGender(GenderEnum.FEMALE);
        contact.setAgent(agentSaved);
        repository.save(contact);

        contact = new Contact();
        contact.setName("Name test 2");
        contact.setPhoneNumber("1177777777");
        contact.setAgeGroup(AgeGroup.ADULT);
        contact.setGender(GenderEnum.FEMALE);
        contact.setAgent(agentSaved);
        repository.save(contact);

        given()
                .contentType(ContentType.JSON)
                .header("X-agent-key", agentSaved.getAgentKey().toString())
                .when()
                .get("/contacts")
                .then()
                .statusCode(200)
                .body(".", hasSize(2));


    }


    @Test
    void shouldUpdateContacts() {
        var agent = new ContactAgent();
        agent.setAgentKey(UUID.randomUUID());
        agent.setName("Name agent");
        agent.setPhoneNumber("1155555555");
        var agentSaved = contactAgentRepository.save(agent);


        var contact = new Contact();
        contact.setName("Name test");
        contact.setPhoneNumber("1199999999");
        contact.setAgeGroup(AgeGroup.ADULT);
        contact.setGender(GenderEnum.FEMALE);
        contact.setAgent(agentSaved);
        Contact saved = repository.save(contact);

        given()
                .header("Content-type", ContentType.JSON)
                .header("X-agent-key", agentSaved.getAgentKey().toString())
                .and()
                .body("""
                        {
                           "name": "Wolley",
                           "gender": "MALE",
                           "ageGroup": "CHILD",
                           "phoneNumber": "11977777777"
                        }
                        """)
                .when()
                .put(String.format("/contacts/%s", saved.getId()))
                .then()
                .statusCode(200)
                .body("name", is("Wolley"))
                .body("gender", is("MALE"))
                .body("ageGroup", is("CHILD"))
                .body("phoneNumber", is("11977777777"));


    }


}
