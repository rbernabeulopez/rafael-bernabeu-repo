package com.example.block7crudvalidation.infrastructure.controller.dto.output;

import lombok.Data;

import java.util.Date;

@Data
public class StudentsStudiesOutputDto {
    private String subjectId;
    //private StudentOutputDto student;
    private String subject;
    private String comments;
    private Date initialDate;
    private Date finishDate;
}

