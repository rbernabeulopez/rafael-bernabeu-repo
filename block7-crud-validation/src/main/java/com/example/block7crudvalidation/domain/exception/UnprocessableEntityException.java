package com.example.block7crudvalidation.domain.exception;

import java.util.Objects;

public class UnprocessableEntityException extends RuntimeException {
    public UnprocessableEntityException(String message) {
        super(message);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UnprocessableEntityException &&
            Objects.equals(((UnprocessableEntityException) obj).getMessage(), this.getMessage());
    }
}
