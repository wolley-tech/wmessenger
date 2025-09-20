package com.wolley.tech.wmessenger.service;

import com.wolley.tech.wmessenger.dto.ContactDTO;
import com.wolley.tech.wmessenger.exception.ContactNotFoundException;
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

        var saved = repository.save(contact);

        return toContactDTO(saved);

    }

    public List<ContactDTO> findAll() {
        List<Contact> contacts = repository.findAll();
        return contacts
                .stream()
                .map(ContactService::toContactDTO).toList();
    }

    public ContactDTO update(Long contactId, ContactDTO contactDTO) {

        if (contactId == null || contactId == 0) {
            throw new IllegalArgumentException("Id do contato não pode ser nulo ou vazio");
        }

        Contact contact = repository.findById(contactId)
                .orElseThrow(() -> new ContactNotFoundException("Contato não encontrado"));

        contact.setName(contactDTO.name());
        contact.setGender(contactDTO.gender());
        contact.setAgeGroup(contactDTO.ageGroup());
        contact.setPhoneNumber(contactDTO.phoneNumber());
        Contact contactUpdated = repository.save(contact);

        return toContactDTO(contactUpdated);
    }


    private static ContactDTO toContactDTO(Contact saved) {
        return ContactDTO.of(
                saved.getId(),
                saved.getName(),
                saved.getGender(),
                saved.getAgeGroup(),
                saved.getPhoneNumber()
        );
    }
}
