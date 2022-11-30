package com.example.block7crudvalidation.infrastructure.controller.dto.input;

import lombok.Data;

@Data
public class ProfessorInputDto {
    private int personId;

    private int numHoursWeek;

    private String comments;

    private String branch;
}
