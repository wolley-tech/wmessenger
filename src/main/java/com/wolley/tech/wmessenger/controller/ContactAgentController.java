package com.wolley.tech.wmessenger.controller;


import com.wolley.tech.wmessenger.dto.ContactAgentDTO;
import com.wolley.tech.wmessenger.service.ContactAgentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact-agents")
@SecurityRequirement(name = "apiKeyAuth")
public class ContactAgentController {
    private final ContactAgentService service;

    public ContactAgentController(ContactAgentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ContactAgentDTO> save(@RequestBody ContactAgentDTO requestDTO){
        var contactAgentDTO = service.save(requestDTO);
        return ResponseEntity
                .status(201)
                .body(contactAgentDTO);
    }

    @GetMapping("/phoneNumber/{phoneNumber}")
    public ResponseEntity<ContactAgentDTO> findByPhoneNumber (@PathVariable String phoneNumber){
        var contractAgentDTO = service.findByPhoneNumber(phoneNumber);
        return ResponseEntity
                .ok(contractAgentDTO);
    }
}
