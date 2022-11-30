package com.example.block10dockerizeapp.infrastructure.controller.dto;

import lombok.Data;

@Data
public class PersonaOutputDto {
    private long id;

    private String nombre;

    private int edad;

    private String  poblacion;
}
