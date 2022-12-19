package com.example.block7crudvalidation.infrastructure.security.authorities;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

public class BaseGrantedAuthority implements GrantedAuthority {
    @Getter
    private final String role;

    public BaseGrantedAuthority(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj instanceof BaseGrantedAuthority && role.equals(((BaseGrantedAuthority) obj).role);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return this.role;
    }
}
