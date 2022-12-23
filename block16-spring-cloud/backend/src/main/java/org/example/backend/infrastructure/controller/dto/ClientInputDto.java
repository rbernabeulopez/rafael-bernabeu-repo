package org.example.backend.infrastructure.controller.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientInputDto {
    private String name;

    private String surname;

    private int age;

    private String email;

    private int phoneNumber;
}
