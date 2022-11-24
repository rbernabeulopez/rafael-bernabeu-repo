package com.example.block7crud.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
