package com.example.block7crudvalidation.infrastructure.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class CustomError {
    LocalDate timestamp;
    Integer httpCode;
    String message;
}
