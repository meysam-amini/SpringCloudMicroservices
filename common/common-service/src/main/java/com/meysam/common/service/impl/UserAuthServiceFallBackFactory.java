package com.meysam.common.service.impl;


import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.model.dto.ClientLoginRequestDto;
import com.meysam.common.model.dto.LoginRequestDto;
import com.meysam.common.model.dto.RegisterUserRequestDto;
import com.meysam.common.service.api.UserAuthServiceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAuthServiceFallBackFactory implements FallbackFactory<UserAuthServiceClient> {

    private final LocaleMessageSourceService messageSourceService;

    @Override
    public UserAuthServiceClient create(Throwable cause) {
        int status = ((FeignException) cause).status();
        String message = cause.getLocalizedMessage();
        return new UserAuthServiceClientFallBack(cause,message,status, messageSourceService);
    }

    @RequiredArgsConstructor
    public class UserAuthServiceClientFallBack implements UserAuthServiceClient {

        private final Throwable cause;
        private final String message;
        private final int status;
        private final LocaleMessageSourceService messageSourceService;


        @Override
        public ResponseEntity clientLogin(ClientLoginRequestDto loginRequestDto) {
            log.error(status+" error occurred when auth-ws /auth-client/login called at time :{}",System.currentTimeMillis());
            return returnProperResponse(cause,status);
        }

        @Override
        public ResponseEntity userLogin(LoginRequestDto loginRequestDto) {
            log.error(status+" error occurred when auth-ws /auth-user/login called at time :{}",System.currentTimeMillis());
            return returnProperResponse(cause,status);
        }

        @Override
        public ResponseEntity registerUser(RegisterUserRequestDto registerRequestDto) {
            log.error(status+" error occurred when auth-ws /auth-user/register called at time :{}",System.currentTimeMillis());
            return returnProperResponse(cause,status);
        }

        @Override
        public ResponseEntity refreshToken() {
            log.error(status+" error occurred when auth-ws /auth-user/refresh-token called at time :{}",System.currentTimeMillis());
            return returnProperResponse(cause,status);
        }

        private ResponseEntity returnProperResponse(Throwable cause, int status){
            if (cause instanceof FeignException && status ==-1) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(messageSourceService.getMessage("AUTH_WS_PROBLEM"));
            } else {
                return ResponseEntity.status(status).body(message);
            }
        }
    }

}
