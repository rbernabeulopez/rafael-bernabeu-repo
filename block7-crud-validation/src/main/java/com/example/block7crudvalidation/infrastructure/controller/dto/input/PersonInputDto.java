package com.example.block7crudvalidation.infrastructure.controller.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PersonInputDto {
    @JsonProperty("usuario")
    private String user;

    private String name;

    private String surname;

    @JsonProperty("company_email")
    private String companyEmail;

    @JsonProperty("personal_email")
    private String personalEmail;

    private String city;

    private boolean active;

    @JsonProperty("created_date")
    private LocalDate createdDate;

    @JsonProperty("imagen_url")
    private String imageUrl;

    private String password;

    @JsonProperty("termination_date")
    private LocalDate terminationDate;
}
