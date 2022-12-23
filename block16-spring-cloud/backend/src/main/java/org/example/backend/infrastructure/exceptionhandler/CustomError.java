package org.example.backend.infrastructure.exceptionhandler;

import lombok.*;

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
