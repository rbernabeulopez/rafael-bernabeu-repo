package com.example.examen_jpa_cascada.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
public class LineaFra {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String proNomb;

    private double cantidad;

    private double precio;

    @ManyToOne
    @JoinColumn(name = "IdFra")
    private CabeceraFra cabeceraFra;

    @Override
    public String toString() {
        return "LineaFra{" +
                "id=" + id +
                ", proNomb='" + proNomb + '\'' +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                '}';
    }
}
