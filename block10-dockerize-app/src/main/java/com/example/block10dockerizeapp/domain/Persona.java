package com.example.block10dockerizeapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Persona {
    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    private int edad;

    private String  poblacion;
}
