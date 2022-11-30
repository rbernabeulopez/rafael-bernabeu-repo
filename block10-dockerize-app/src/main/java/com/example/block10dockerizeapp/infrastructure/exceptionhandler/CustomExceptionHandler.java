package com.example.block10dockerizeapp.infrastructure.exceptionhandler;

import com.example.block10dockerizeapp.application.exception.PersonaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PersonaNotFoundException.class)
    public ExceptionResponse handlePersonaNotFoundExceptions(PersonaNotFoundException ex) {
        return ExceptionResponse.builder()
            .message(ex.getMessage())
            .creationDate(LocalDateTime.now())
            .build();
    }
}
