package com.example.block7crudvalidation.domain.exception;

import java.util.Objects;

public class UnprocessableEntityException extends RuntimeException {
    public UnprocessableEntityException(String message) {
        super(message);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UnprocessableEntityException unprocessableEntityException &&
            Objects.equals(unprocessableEntityException.getMessage(), this.getMessage());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
