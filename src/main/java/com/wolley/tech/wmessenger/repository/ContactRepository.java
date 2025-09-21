package com.wolley.tech.wmessenger.repository;

import com.wolley.tech.wmessenger.model.Contact;
import com.wolley.tech.wmessenger.model.ContactAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByAgent(ContactAgent contactAgent);
}
