package com.example.block7crudvalidation.infrastructure.controller.dto;

import lombok.Data;

@Data
public class StudentInputDto {
    private int personId;

    private int numHoursWeek;

    private String comments;

    // TODO: Añadir relación estudiante profesor
    // private String professorId;

    private String branch;

    // TODO: Añadir relación estudiante asignatura-estudiante
}
