package com.wolley.tech.wmessenger.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 255)
    private String name;
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    @Column(name = "gender", length = 20)
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    @Column(name = "age_group", length = 20)
    @Enumerated(EnumType.STRING)
    private AgeGroup ageGroup;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "agent_id")
    private ContactAgent agent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }


    public ContactAgent getAgent() {
        return agent;
    }

    public void setAgent(ContactAgent agent) {
        this.agent = agent;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id) && Objects.equals(name, contact.name) && Objects.equals(phoneNumber, contact.phoneNumber) && gender == contact.gender && ageGroup == contact.ageGroup;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phoneNumber, gender, ageGroup);
    }
}
