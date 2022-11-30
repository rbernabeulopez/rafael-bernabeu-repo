package com.example.block7crudvalidation.infrastructure.controller.dto.input;

import lombok.Data;

@Data
public class StudentInputDto {
    private int personId;

    private int numHoursWeek;

    private String comments;

    private String professorId;

    private String branch;
}
