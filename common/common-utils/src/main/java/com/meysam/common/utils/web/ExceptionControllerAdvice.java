package com.meysam.common.utils.web;

import com.meysam.common.utils.exception.BusinessException;
import com.meysam.common.utils.exception.KeycloakException;
import com.meysam.common.utils.messages.LocaleMessageSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeoutException;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

    private final LocaleMessageSourceService messageSourceService;

    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity marketBusinessExceptionHandler(BusinessException ex) {
        log.error("handelling BusinessException at time :{}",System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = KeycloakException.class)
    public ResponseEntity keycloakExceptionHandler(KeycloakException ex) {
        log.error("handelling KeycloakException at time :{}",System.currentTimeMillis());
        return ResponseEntity.status(ex.getHttpStatusCode()).body(ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity handleBadRequest(BindException exception){
        log.error("handelling bad request at time :{}",System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageSourceService.getMessage("BAD_REQUEST"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalArgumentException(IllegalArgumentException exception){
        log.error("handelling IllegalArgumentException at time :{}",System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageSourceService.getMessage("CONTACT_WITH_SUPPORT_TEAM"));
    }

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity error(TimeoutException exception){
        log.error("handelling TimeoutException at time :{}",System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageSourceService.getMessage("SERVICE_UNAVAILABLE"));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity error(HttpRequestMethodNotSupportedException exception){
        log.error("handelling HttpRequestMethodNotSupportedException at time :{}",System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(messageSourceService.getMessage("WRONG_HTTP_METHOD"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity error(Exception exception){
        log.error("handelling exception at time :{}",System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageSourceService.getMessage("CONTACT_WITH_SUPPORT_TEAM"));
    }
}
