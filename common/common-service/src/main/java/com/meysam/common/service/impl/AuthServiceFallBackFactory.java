package com.meysam.common.service.impl;


import com.meysam.common.model.dto.ClientLoginRequestDto;
import com.meysam.common.model.dto.LoginRequestDto;
import com.meysam.common.model.dto.RegisterUserRequestDto;
import com.meysam.common.service.api.AuthServiceClient;
import com.meysam.common.utils.messages.LocaleMessageSourceService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthServiceFallBackFactory implements FallbackFactory<AuthServiceClient> {

    private final LocaleMessageSourceService messageSourceService;

    @Override
    public AuthServiceClient create(Throwable cause) {
        return new AuthServiceClientFallBack(cause, messageSourceService);
    }

    @RequiredArgsConstructor
    class AuthServiceClientFallBack implements AuthServiceClient {

        private final Throwable cause;
        private final LocaleMessageSourceService messageSourceService;


        @Override
        public ResponseEntity clientLogin(ClientLoginRequestDto loginRequestDto) {
            if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
                log.error("404 error occurred when auth-ws /auth-client/login called");
            } else {
                log.error("Other error took place: " + cause.getLocalizedMessage());
            }

            return ResponseEntity.ok(messageSourceService.getMessage("AUTH_WS_PROBLEM"));
        }

        @Override
        public ResponseEntity userLogin(LoginRequestDto loginRequestDto) {
            if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
                log.error("404 error occurred when auth-ws /auth-user/login called");
            } else {
                log.error("Other error took place: " + cause.getLocalizedMessage());
            }

            return ResponseEntity.ok(messageSourceService.getMessage("AUTH_WS_PROBLEM"));
        }

        @Override
        public ResponseEntity registerUser(RegisterUserRequestDto registerRequestDto) {
            if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
                log.error("404 error occurred when auth-ws /auth-user/register called");
            } else {
                log.error("Other error took place: " + cause.getLocalizedMessage());
            }

            return ResponseEntity.ok(messageSourceService.getMessage("AUTH_WS_PROBLEM"));
        }

        @Override
        public ResponseEntity refreshToken() {
            if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
                log.error("404 error occurred when auth-ws /auth-user/refresh-token called");
            } else {
                log.error("Other error took place: " + cause.getLocalizedMessage());
            }

            return ResponseEntity.ok(messageSourceService.getMessage("AUTH_WS_PROBLEM"));
        }
    }

}
