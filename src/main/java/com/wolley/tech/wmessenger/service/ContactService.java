package com.wolley.tech.wmessenger.service;

import com.wolley.tech.wmessenger.dto.ContactDTO;
import com.wolley.tech.wmessenger.exception.ContactAgentNotFoundException;
import com.wolley.tech.wmessenger.exception.ContactNotFoundException;
import com.wolley.tech.wmessenger.model.Contact;
import com.wolley.tech.wmessenger.repository.ContactAgentRepository;
import com.wolley.tech.wmessenger.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactService {
    private final ContactRepository repository;
    private final ContactAgentRepository contactAgentRepository;

    public ContactService(ContactRepository repository,
                          ContactAgentRepository contactAgentRepository) {
        this.repository = repository;
        this.contactAgentRepository = contactAgentRepository;
    }

    public ContactDTO save(ContactDTO dto, UUID agentKey) {

        if (agentKey == null){
            throw new IllegalArgumentException("O parâmetro no header X-agent-key n]ao pode ser nulo ou vazio");
        }

        var contactAgent= contactAgentRepository.findByAgentKey(agentKey)
                .orElseThrow(() -> new ContactAgentNotFoundException("Agente não encontrado"));

        var contact = new Contact();
        contact.setName(dto.name());
        contact.setGender(dto.gender());
        contact.setAgeGroup(dto.ageGroup());
        contact.setPhoneNumber(dto.phoneNumber());
        contact.setAgent(contactAgent);

        var saved = repository.save(contact);

        return toContactDTO(saved);

    }

    public List<ContactDTO> findByAgent(UUID agentKey) {
        if (agentKey == null){
            throw new IllegalArgumentException("O parâmetro no header X-agent-key n]ao pode ser nulo ou vazio");
        }

        var contactAgent= contactAgentRepository.findByAgentKey(agentKey)
                .orElseThrow(() -> new ContactAgentNotFoundException("Agente não encontrado"));

        List<Contact> contacts = repository.findByAgent(contactAgent);
        return contacts
                .stream()
                .map(ContactService::toContactDTO).toList();
    }

    public ContactDTO update(Long contactId, ContactDTO contactDTO, UUID agentKey) {

        if (contactId == null || contactId == 0) {
            throw new IllegalArgumentException("Id do contato não pode ser nulo ou vazio");
        }

        if (agentKey == null){
            throw new IllegalArgumentException("O parâmetro no header X-agent-key n]ao pode ser nulo ou vazio");
        }

        var contactAgent= contactAgentRepository.findByAgentKey(agentKey)
                .orElseThrow(() -> new ContactAgentNotFoundException("Agente não encontrado"));

        Contact contact = repository.findById(contactId)
                .orElseThrow(() -> new ContactNotFoundException("Contato não encontrado"));

        contact.setName(contactDTO.name());
        contact.setGender(contactDTO.gender());
        contact.setAgeGroup(contactDTO.ageGroup());
        contact.setPhoneNumber(contactDTO.phoneNumber());
        contact.setAgent(contactAgent);
        Contact contactUpdated = repository.save(contact);


        return toContactDTO(contactUpdated);
    }


    private static ContactDTO toContactDTO(Contact saved) {
        return ContactDTO.of(
                saved.getId(),
                saved.getName(),
                saved.getGender(),
                saved.getAgeGroup(),
                saved.getPhoneNumber(),
                saved.getAgent().getAgentKey().toString()
        );
    }
}
