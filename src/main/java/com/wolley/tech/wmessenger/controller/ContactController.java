package com.wolley.tech.wmessenger.controller;

import com.wolley.tech.wmessenger.dto.ContactDTO;
import com.wolley.tech.wmessenger.service.ContactService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contacts")
@SecurityRequirement(name = "apiKeyAuth")
public class ContactController {
    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ContactDTO> save(@RequestHeader("X-agent-key")UUID agentKey,
                                           @RequestBody ContactDTO requestDTO) {
        var contactSaved = service.save(requestDTO, agentKey);
        return ResponseEntity
                .status(201)
                .body(contactSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> update(@RequestHeader("X-agent-key") UUID agentKey,
                                             @PathVariable Long id,
                                             @RequestBody ContactDTO contactDTO) {
        var contactUpdated = service.update(id, contactDTO, agentKey);
        return ResponseEntity
                .ok(contactUpdated);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> findById(@RequestHeader("X-agent-key") UUID agentKey,
                                             @PathVariable Long id) {
        var contactDTO = service.findById(id);
        return ResponseEntity
                .ok(contactDTO);
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> findAll(@RequestHeader("X-agent-key") UUID agentKey) {
        List<ContactDTO> contacts = service.findByAgent(agentKey);
        return ResponseEntity.ok(contacts);
    }
}
