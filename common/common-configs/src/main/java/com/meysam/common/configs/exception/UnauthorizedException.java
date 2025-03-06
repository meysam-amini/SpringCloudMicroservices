package com.meysam.common.configs.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class UnauthorizedException extends RuntimeException{

    private HttpStatusCode httpStatusCode;

    public UnauthorizedException(String message,HttpStatus httpStatus){
        super(message);
        this.httpStatusCode = httpStatus;
    }
}
