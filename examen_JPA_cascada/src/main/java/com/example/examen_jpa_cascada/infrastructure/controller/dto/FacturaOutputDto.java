package com.example.examen_jpa_cascada.infrastructure.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class FacturaOutputDto {
    int id;

    double importeFra = 0;

    ClienteOutputDto cliente;

    List<LineaOutputDto> lineasFra;
}
