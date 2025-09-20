package com.wolley.tech.wmessenger.exception;

public class ContactNotFoundException extends RuntimeException {

    public ContactNotFoundException() {
        super();
    }

    public ContactNotFoundException(String message) {
        super(message);
    }
}
