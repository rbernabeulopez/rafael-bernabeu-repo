package com.example.examen_jpa_cascada.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class CabeceraFra {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "cli_codi")
    private Cliente cliente;

    private double importeFra;

    @OneToMany(mappedBy = "cabeceraFra", cascade = CascadeType.ALL)
    private List<LineaFra> lineasFra = new ArrayList<>();
}
