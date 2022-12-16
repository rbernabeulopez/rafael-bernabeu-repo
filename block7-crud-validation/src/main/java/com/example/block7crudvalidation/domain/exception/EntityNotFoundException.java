package com.example.block7crudvalidation.domain.exception;

import java.util.Objects;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EntityNotFoundException entitynotfoundexception &&
                Objects.equals(entitynotfoundexception.getMessage(), this.getMessage());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
