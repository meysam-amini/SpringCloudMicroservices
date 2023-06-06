package com.meysam.controller.exception;

import com.meysam.utils.LocaleMessageSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    private final LocaleMessageSourceService messageSourceService;

    @ExceptionHandler(BindException.class)
    public ResponseEntity handleBadRequest(Exception exception){
        log.error("Handling Bad Request Exception");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageSourceService.getMessage("BAD_REQUEST"));
    }
}
