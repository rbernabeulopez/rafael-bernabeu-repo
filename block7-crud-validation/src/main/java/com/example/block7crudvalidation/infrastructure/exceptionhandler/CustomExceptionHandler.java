package com.example.block7crudvalidation.infrastructure.exceptionhandler;

import com.example.block7crudvalidation.domain.exception.EntityNotFoundException;
import com.example.block7crudvalidation.domain.exception.UnprocessableEntityException;
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public CustomError handleEntityNotFoundException(EntityNotFoundException ex) {
        return generateError(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnprocessableEntityException.class)
    public CustomError handleEntityNotFoundException(UnprocessableEntityException ex) {
        return generateError(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
