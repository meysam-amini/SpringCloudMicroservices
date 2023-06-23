package com.meysam.users.service.impl;

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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WalletServiceFallBackFactory implements FallbackFactory<WalletServiceClient> {

    private final LocaleMessageSourceService messageSourceService;

    @Override
    public WalletServiceClient create(Throwable cause) {
        return new WalletServiceClientFallBack(cause, messageSourceService);
    }

    @RequiredArgsConstructor
    public class WalletServiceClientFallBack implements WalletServiceClient {

        private final Throwable cause;
        private final LocaleMessageSourceService messageSourceService;


        @Override
        public ResponseEntity<String> createWallet(MemberWalletDto memberWalletDto) {
            if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
                log.error("404 error occurred when createWallet called for inputs: "
                        + memberWalletDto.toString() + " . Error message: "
                        + cause.getLocalizedMessage());
            } else {
                log.error("Other error took place: " + cause.getLocalizedMessage());
            }

            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(messageSourceService.getMessage("WALLET_WS_PROBLEM"));
        }

        @Override
        public ResponseEntity<List<MemberWalletDto>> getWallets(String username) {

            if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
                log.error("404 error occurred when getWallets called for username: "
                        + username + " . Error message: "
                        + cause.getLocalizedMessage());
            } else {
                log.error("Other error took place: " + cause.getLocalizedMessage());
            }
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ArrayList<>());
        }
    }

}