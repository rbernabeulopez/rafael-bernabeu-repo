package com.example.block7crudvalidation.infrastructure.controller.dto.input;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonInputDto {
    private String user;

    private String name;

    private String surname;

    private String companyEmail;

    private String personalEmail;

    private String city;

    private String imageUrl;

    private String password;
}
