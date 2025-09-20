package com.wolley.tech.wmessenger.controller;

import com.wolley.tech.wmessenger.dto.ContactDTO;
import com.wolley.tech.wmessenger.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ContactDTO> save(@RequestBody ContactDTO requestDTO) {
        var contactSaved = service.save(requestDTO);
        return ResponseEntity
                .status(201)
                .body(contactSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> update(@PathVariable Long id,
                                             @RequestBody ContactDTO contactDTO) {
        var contactUpdated = service.update(id, contactDTO);
        return ResponseEntity
                .ok(contactUpdated);
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> findAll() {
        List<ContactDTO> contacts = service.findAll();
        return ResponseEntity.ok(contacts);
    }
}
