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
    public ResponseEntity<String> businessExceptionHandler(BusinessException ex) {
        log.error("handling BusinessException at time :{}, exception is : {}",System.currentTimeMillis(),ex);
        return ResponseEntity.status(ex.getHttpStatusCode()).body(ex.getLocalizedMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = KeycloakException.class)
    public ResponseEntity<String>  keycloakExceptionHandler(KeycloakException ex) {
        log.error("handling KeycloakException at time :{}, exception is : {}",System.currentTimeMillis(),ex);
        return ResponseEntity.status(ex.getHttpStatusCode()).body(ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<String>  handleBadRequest(BindException exception){
        log.error("handling bad request at time :{}, exception is : {}",System.currentTimeMillis(),exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageSourceService.getMessage("BAD_REQUEST"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String>  illegalArgumentException(IllegalArgumentException exception){
        log.error("handling IllegalArgumentException at time :{} , exception is : {}",System.currentTimeMillis(),exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageSourceService.getMessage("CONTACT_WITH_SUPPORT_TEAM"));
    }

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<String>  timeoutException(TimeoutException exception){
        log.error("handling TimeoutException at time :{} , exception is : {}",System.currentTimeMillis(),exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageSourceService.getMessage("SERVICE_UNAVAILABLE"));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String>  httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception){
        log.error("handling HttpRequestMethodNotSupportedException at time :{} , exception is : {}",System.currentTimeMillis(),exception);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(messageSourceService.getMessage("WRONG_HTTP_METHOD"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String>  exception(Exception exception){
        log.error("handling exception at time :{} , exception is : {}",System.currentTimeMillis(),exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageSourceService.getMessage("CONTACT_WITH_SUPPORT_TEAM"));
    }
}
