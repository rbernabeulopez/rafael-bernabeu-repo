package com.example.examen_jpa_cascada.infrastructure.controller.dto;

import lombok.Data;

@Data
public class LineaInputDto {
    String producto;

    double cantidad;

    double importe;

    int idFra;
}
