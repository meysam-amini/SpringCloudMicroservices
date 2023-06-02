package com.meysam.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity handleBadRequest(Exception exception){
        log.error("Handling Bad Request Exception");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getCause());
    }
}
