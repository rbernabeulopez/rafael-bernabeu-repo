package org.example.backendFront.domain.exception;

public class RestException extends RuntimeException {
    public RestException(String message) {
        super(message);
    }
}
