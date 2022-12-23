package org.example.backendFront.infrastructure.exceptionHandler;

import org.example.backendFront.domain.exception.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private CustomError generateError(String message, HttpStatus httpStatus) {
        return CustomError.builder()
            .message(message)
            .httpCode(httpStatus.value())
            .timestamp(LocalDate.now())
            .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RestException.class)
    public CustomError handleRestException(RestException ex) {
        return generateError(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
