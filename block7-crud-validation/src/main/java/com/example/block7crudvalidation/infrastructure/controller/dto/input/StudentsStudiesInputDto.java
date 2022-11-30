package com.example.block7crudvalidation.infrastructure.controller.dto.input;

import lombok.Data;

import java.util.Date;

@Data
public class StudentsStudiesInputDto {
    private String studentId;

    private String subject;

    private String comments;

    private Date initialDate;

    private Date finishDate;
}
