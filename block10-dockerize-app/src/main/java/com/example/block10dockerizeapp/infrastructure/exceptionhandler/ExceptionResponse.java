package com.example.block10dockerizeapp.infrastructure.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ExceptionResponse {
    private LocalDateTime creationDate;
    private String message;
}
