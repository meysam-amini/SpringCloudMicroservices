package com.meysam.apigateway.filter;

import org.springframework.web.server.ResponseStatusException;
import java.util.function.Predicate;

public class HttpInternalServicePredicate implements Predicate<ResponseStatusException> {
    @Override
    public boolean test(ResponseStatusException e) {
        return e.getStatusCode().is4xxClientError();
    }
}
