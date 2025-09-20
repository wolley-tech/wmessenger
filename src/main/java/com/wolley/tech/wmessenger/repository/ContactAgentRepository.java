package com.wolley.tech.wmessenger.repository;

import com.wolley.tech.wmessenger.model.ContactAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactAgentRepository extends JpaRepository<ContactAgent, Long> {
    Optional<ContactAgent> findByPhoneNumber(String phoneNumber);
}
