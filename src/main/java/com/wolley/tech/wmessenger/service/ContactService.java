package com.wolley.tech.wmessenger.service;

import com.wolley.tech.wmessenger.dto.ContactDTO;
import com.wolley.tech.wmessenger.model.Contact;
import com.wolley.tech.wmessenger.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository repository;

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    public ContactDTO save(ContactDTO dto) {
        var contact = new Contact();
        contact.setName(dto.name());
        contact.setGender(dto.gender());
        contact.setAgeGroup(dto.ageGroup());
        contact.setPhoneNumber(dto.phoneNumber());

        Contact saved = repository.save(contact);

        return ContactDTO.of(
                saved.getId(),
                saved.getName(),
                saved.getGender(),
                saved.getAgeGroup(),
                saved.getPhoneNumber()
        );

    }

    public List<ContactDTO> findAll() {
        List<Contact> contacts = repository.findAll();
        return contacts
                .stream()
                .map(contact -> {
                    return ContactDTO.of(
                            contact.getId(),
                            contact.getName(),
                            contact.getGender(),
                            contact.getAgeGroup(),
                            contact.getPhoneNumber()
                    );
                }).toList();
    }
}
