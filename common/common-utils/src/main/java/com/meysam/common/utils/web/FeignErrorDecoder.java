package com.meysam.common.utils.web;

import com.meysam.common.utils.exception.BusinessException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        throw new BusinessException(response.reason());
    }
}
