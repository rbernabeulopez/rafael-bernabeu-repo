package com.example.block7crudvalidation.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "username", nullable = false)
    private String user;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String surname;

    @Column(name = "company_email", nullable = false)
    private String companyEmail;

    @Column(name = "personal_email", nullable = false)
    private String personalEmail;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "termination_date")
    private LocalDate terminationDate;
}
