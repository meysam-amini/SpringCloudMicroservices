package com.meysam.common.utils.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class BusinessException extends RuntimeException{

    private HttpStatusCode httpStatusCode;

    public BusinessException(String message){
        super(message);
    }

    public BusinessException(HttpStatusCode httpStatusCode,String message){
        super(message);
        this.httpStatusCode=httpStatusCode;
    }
}
