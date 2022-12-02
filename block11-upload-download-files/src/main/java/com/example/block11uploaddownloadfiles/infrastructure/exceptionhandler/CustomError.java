package com.example.block11uploaddownloadfiles.infrastructure.exceptionhandler;

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
