package org.example.openfeignclients.backend.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ClientOutputDto {
    private long id;

    private String name;

    private String surname;

    private int age;

    private String email;

    private int phoneNumber;
}
