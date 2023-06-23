package com.meysam.users.service.impl;

import com.meysam.common.utils.exception.ServicesException;
import com.meysam.common.utils.messages.LocaleMessageSourceService;
import com.meysam.users.service.api.WalletServiceClient;
import com.meysam.common.model.dto.MemberWalletDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WalletServiceFallBackFactory implements FallbackFactory<WalletServiceClient> {

    private final LocaleMessageSourceService messageSourceService;

    @Override
    public WalletServiceClient create(Throwable cause) {
        int status = ((FeignException) cause).status();
        String message = cause.getLocalizedMessage();
        return new WalletServiceClientFallBack(cause,message,status, messageSourceService);
    }

    @RequiredArgsConstructor
    public class WalletServiceClientFallBack implements WalletServiceClient {

        private final Throwable cause;
        private final String message;
        private final int status;
        private final LocaleMessageSourceService messageSourceService;


        @Override
        public ResponseEntity<String> createWallet(String token,MemberWalletDto memberWalletDto) {
            log.error(status+" error occurred when createWallet method called /member-wallet/create at time :{}",System.currentTimeMillis());
            return returnProperResponse(cause,status);
        }

        @Override
        public List<MemberWalletDto> getWallets(String token, String username) {
            log.error(status+" error occurred when createWallet method called /member-wallet/wallets/"+username+" at time :{}",System.currentTimeMillis());
            ResponseEntity response = returnProperResponse(cause,status);
            throw new ServicesException(response.getBody().toString(),response.getStatusCode());
        }



        private ResponseEntity returnProperResponse(Throwable cause, int status){
            if (cause instanceof FeignException && status ==-1) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(messageSourceService.getMessage("WALLET_WS_PROBLEM"));
            } else {
                return ResponseEntity.status(status).body(message);
            }
        }
    }

}