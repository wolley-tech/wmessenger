package com.wolley.tech.wmessenger.dto;

import com.wolley.tech.wmessenger.model.AgeGroup;
import com.wolley.tech.wmessenger.model.GenderEnum;

public record ContactDTO(Long id,
                         String name,
                         GenderEnum gender,
                         AgeGroup ageGroup,
                         String phoneNumber) {



    public static ContactDTO of (Long id,
                                 String name,
                                 GenderEnum gender,
                                 AgeGroup ageGroup,
                                 String phoneNumber){
        return new ContactDTO(
                id,
                name,
                gender,
                ageGroup,
                phoneNumber
        );
    }
}
