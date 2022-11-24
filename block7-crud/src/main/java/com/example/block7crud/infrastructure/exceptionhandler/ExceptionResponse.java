package com.example.block7crud.infrastructure.exceptionhandler;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
public class ExceptionResponse {
    private LocalDateTime creationDate;
    private String message;
}
