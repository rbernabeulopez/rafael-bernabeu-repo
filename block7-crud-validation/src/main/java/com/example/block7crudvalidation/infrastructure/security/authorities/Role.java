package com.example.block7crudvalidation.infrastructure.security.authorities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum Role implements GrantedAuthority {
    ROLE_ADMIN("Administrator"),
    ROLE_USER("User");

    @Getter
    private final String label;

    @Override
    public String getAuthority() {
        return name();
    }
}
