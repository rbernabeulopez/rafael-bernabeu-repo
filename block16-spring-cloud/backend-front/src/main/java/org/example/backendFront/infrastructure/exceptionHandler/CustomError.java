package org.example.backendFront.infrastructure.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomError {
    LocalDate timestamp;
    Integer httpCode;
    String message;
}
