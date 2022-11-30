package com.example.block7crudvalidation.infrastructure.controller.dto.output;

import lombok.Data;

@Data
public class ProfessorFullOutputDto extends ProfessorOutputDto {
    private PersonOutputDto person;
}

