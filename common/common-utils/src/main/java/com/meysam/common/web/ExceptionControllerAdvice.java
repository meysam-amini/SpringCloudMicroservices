package com.meysam.common.web;

import com.meysam.common.exception.BusinessException;
import com.meysam.common.messages.LocaleMessageSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

    private final LocaleMessageSourceService messageSourceService;

    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity marketBusinessExceptionHandler(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity handleBadRequest(Exception exception){
        log.error("Handling Bad Request Exception: {}",exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageSourceService.getMessage("BAD_REQUEST"));
    }
}
