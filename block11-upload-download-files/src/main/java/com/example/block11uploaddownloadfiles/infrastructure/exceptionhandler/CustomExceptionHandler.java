package com.example.block11uploaddownloadfiles.infrastructure.exceptionhandler;

import com.example.block11uploaddownloadfiles.domain.exception.FileNotFoundException;
import com.example.block11uploaddownloadfiles.domain.exception.FileUploadException;
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
    @ExceptionHandler(FileNotFoundException.class)
    public CustomError handleEntityNotFoundException(FileNotFoundException ex) {
        return generateError(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(FileUploadException.class)
    public CustomError handleEntityNotFoundException(FileUploadException ex) {
        return generateError(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
