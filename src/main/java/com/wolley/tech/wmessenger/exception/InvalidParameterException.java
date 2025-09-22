package com.wolley.tech.wmessenger.exception;

public class InvalidParameterException extends IllegalArgumentException {
    public InvalidParameterException() {
        super();
    }

    public InvalidParameterException(String s) {
        super(s);
    }
}
