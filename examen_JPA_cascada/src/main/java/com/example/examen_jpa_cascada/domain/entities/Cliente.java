package com.example.examen_jpa_cascada.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Cliente {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String nombre;
}
