package com.wolley.tech.wmessenger.exception;

public class ContactAgentNotFoundException extends RuntimeException{

    public ContactAgentNotFoundException() {
        super();
    }

    public ContactAgentNotFoundException(String message) {
        super(message);
    }
}
