package com.meysam.common.utils.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class ServicesException extends RuntimeException{

    private HttpStatusCode httpStatusCode;

    public ServicesException(String message, HttpStatusCode httpStatusCode){
        super(message);
        this.httpStatusCode=httpStatusCode;
    }
}
