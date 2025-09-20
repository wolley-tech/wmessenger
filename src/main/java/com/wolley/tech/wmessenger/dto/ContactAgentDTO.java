package com.wolley.tech.wmessenger.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ContactAgentDTO(
        Long id,
        UUID agentKey,
        @NotNull(message = "O campo nome não pode ser vazio")
        String name,
        @NotNull(message = "O campo número de telefone não pode ser vazio")
        String phoneNumber
) {
}
