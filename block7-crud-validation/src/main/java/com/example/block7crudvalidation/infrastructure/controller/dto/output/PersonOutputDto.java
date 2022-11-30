package com.example.block7crudvalidation.infrastructure.controller.dto.output;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
public class PersonOutputDto {
    private int id;

    private String user;

    private String name;

    private String surname;

    private String companyEmail;

    private String personalEmail;

    private String city;

    private boolean active;

    private LocalDate createdDate;

    private String imageUrl;

    private LocalDate terminationDate;
}
