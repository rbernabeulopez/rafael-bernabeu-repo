package org.example.backend.domain.exception;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException(String message) {
        super(message);
    }
}
