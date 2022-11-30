package com.example.block7crudvalidation.infrastructure.controller.dto.output;

import lombok.Data;

import java.util.List;

@Data
public class ProfessorOutputDto {
    private String professorId;

    private int numHoursWeek;

    private String comments;

    private List<StudentOutputDto> students;

    private String branch;
}

