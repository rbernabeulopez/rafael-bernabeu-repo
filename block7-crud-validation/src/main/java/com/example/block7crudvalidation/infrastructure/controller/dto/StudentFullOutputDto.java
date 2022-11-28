package com.example.block7crudvalidation.infrastructure.controller.dto;

import com.example.block7crudvalidation.domain.entity.Person;
import lombok.Data;

@Data
public class StudentFullOutputDto extends StudentOutputDto {
    private Person person;
}

