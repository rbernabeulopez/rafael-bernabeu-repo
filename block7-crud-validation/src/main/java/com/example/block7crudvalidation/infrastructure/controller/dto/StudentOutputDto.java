package com.example.block7crudvalidation.infrastructure.controller.dto;

import lombok.Data;

@Data
public class StudentOutputDto {
    private String studentId;

    private int numHoursWeek;

    private String comments;

    // TODO: Añadir relación estudiante profesor
    // private Professor professor;

    private String branch;

    // TODO: Añadir relación estudiante asignatura-estudiante
}

