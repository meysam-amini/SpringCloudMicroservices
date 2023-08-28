package com.meysam.apigateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/fallback")
public class GatewayFallback {


    @GetMapping("/testInternalServiceError")
    Flux<Void> getFallBackBackendB() {
        log.info("Fallback for book service");
        return Flux.empty();
    }
}
