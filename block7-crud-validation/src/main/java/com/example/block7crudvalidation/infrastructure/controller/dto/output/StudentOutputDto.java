package com.example.block7crudvalidation.infrastructure.controller.dto.output;

import lombok.Data;

import java.util.List;

@Data
public class StudentOutputDto {
    private String studentId;

    private int numHoursWeek;

    private String comments;

    private String branch;

    private List<StudentsStudiesOutputDto> studies;
}

