package com.meysam.users.service.impl;

import com.meysam.common.utils.messages.LocaleMessageSourceService;
import com.meysam.users.service.api.WalletServiceClient;
import com.meysam.walletmanager.model.dto.MemberWalletDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class WalletServiceFallBackFactory implements FallbackFactory<WalletServiceClient> {

    private final LocaleMessageSourceService messageSourceService;

    @Override
    public WalletServiceClient create(Throwable cause) {
        return new WalletServiceClientFallBack(cause, messageSourceService);
    }

    @Slf4j
    @RequiredArgsConstructor
    class WalletServiceClientFallBack implements WalletServiceClient {

        private final Throwable cause;
        private final LocaleMessageSourceService messageSourceService;


        @Override
        public ResponseEntity createWallet(MemberWalletDto memberWalletDto) {
            if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
                log.error("404 error occurred when createWallet called for inputs: "
                        + memberWalletDto.toString() + " . Error message: "
                        + cause.getLocalizedMessage());
            } else {
                log.error("Other error took place: " + cause.getLocalizedMessage());
            }

            return ResponseEntity.ok(messageSourceService.getMessage("WALLET_WS_PROBLEM"));
        }

        @Override
        public ResponseEntity getWallets(BigDecimal id) {

            if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
                log.error("404 error occurred when getWallets called for userID: "
                        + id + " . Error message: "
                        + cause.getLocalizedMessage());
            } else {
                log.error("Other error took place: " + cause.getLocalizedMessage());
            }

            return ResponseEntity.ok(messageSourceService.getMessage("WALLET_WS_PROBLEM"));
        }
    }

}