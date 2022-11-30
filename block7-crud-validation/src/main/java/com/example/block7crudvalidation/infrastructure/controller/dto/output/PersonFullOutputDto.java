package com.example.block7crudvalidation.infrastructure.controller.dto.output;

import lombok.Data;

@Data
public class PersonFullOutputDto extends PersonOutputDto {
    private ProfessorOutputDto professor;
    private StudentOutputDto student;
}

