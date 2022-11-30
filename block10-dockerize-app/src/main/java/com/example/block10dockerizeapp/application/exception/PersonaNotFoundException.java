package com.example.block10dockerizeapp.application.exception;

public class PersonaNotFoundException extends RuntimeException {
    public PersonaNotFoundException(String message) {
        super(message);
    }
}
