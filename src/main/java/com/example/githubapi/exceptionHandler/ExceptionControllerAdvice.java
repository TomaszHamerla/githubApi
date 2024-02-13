package com.example.githubapi.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ErrorResponse handleException(HttpClientErrorException e) {
        String message = ("User with given name do not exists !");
        ErrorResponse errorResponse = new ErrorResponse(404, message);
        log.error("User with given name do not exists !");
        return errorResponse;
    }

}
