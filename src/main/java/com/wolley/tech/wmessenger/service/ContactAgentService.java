package com.wolley.tech.wmessenger.service;

import com.wolley.tech.wmessenger.dto.ContactAgentDTO;
import com.wolley.tech.wmessenger.exception.ContactAgentNotFoundException;
import com.wolley.tech.wmessenger.exception.InvalidParameterException;
import com.wolley.tech.wmessenger.model.ContactAgent;
import com.wolley.tech.wmessenger.repository.ContactAgentRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContactAgentService {
    public static final String INVALID_PHONENUMBER_PARAM = "O número do telefone não pode ser nulo ou vazio";
    public static final String AGENT_NOT_FOUND = "Agente não cadastrado";
    private final ContactAgentRepository repository;

    public ContactAgentService(ContactAgentRepository repository) {
        this.repository = repository;
    }

    public ContactAgentDTO save(ContactAgentDTO dto) {
        var agent = new ContactAgent();
        agent.setAgentKey(UUID.randomUUID());
        agent.setName(dto.name());
        agent.setPhoneNumber(dto.phoneNumber());

        var agentSaved = repository.save(agent);
        return toContactAgentDTO(agentSaved);
    }


    public ContactAgentDTO findByPhoneNumber(String phoneNumber) {

        if (StringUtils.isEmpty(phoneNumber)){
            throw new InvalidParameterException(INVALID_PHONENUMBER_PARAM);
        }

        var contactAgent = repository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ContactAgentNotFoundException(AGENT_NOT_FOUND));

        return toContactAgentDTO(contactAgent);
    }



    private ContactAgentDTO toContactAgentDTO(ContactAgent agent) {
        return new ContactAgentDTO(
                agent.getId(),
                agent.getAgentKey(),
                agent.getName(),
                agent.getPhoneNumber()
        );
    }
}
