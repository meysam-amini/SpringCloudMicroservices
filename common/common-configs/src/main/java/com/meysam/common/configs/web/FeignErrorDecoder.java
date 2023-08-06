package com.meysam.common.configs.web;

import com.meysam.common.configs.exception.ServicesException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatusCode;

//@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        throw new ServicesException(response.reason(), HttpStatusCode.valueOf(response.status()));
    }
}
