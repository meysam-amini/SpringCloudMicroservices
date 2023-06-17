package com.meysam.walletservice.service;

import com.meysam.common.utils.messages.LocaleMessageSourceService;
import com.meysam.walletmanager.model.dto.MemberWalletDto;
import feign.FeignException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

@FeignClient(name = "wallet-ws",fallbackFactory = WalletServiceFallBackFactory.class)
public interface WalletServiceClient {

    @GetMapping("/member-wallet/wallets/create")
    public ResponseEntity createWallet(@RequestBody @Valid MemberWalletDto memberWalletDto);

    @GetMapping("/member-wallet/wallets/{memberId}")
    public ResponseEntity getWallets(@PathVariable("memberId") BigDecimal memberId);




}

@Component
@RequiredArgsConstructor
class WalletServiceFallBackFactory implements FallbackFactory<WalletServiceClient>{

    private final LocaleMessageSourceService messageSourceService;

    @Override
    public WalletServiceClient create(Throwable cause) {
        return new WalletServiceClientFallBack(cause,messageSourceService);
    }
}
@Slf4j
@RequiredArgsConstructor
class WalletServiceClientFallBack implements WalletServiceClient {

    private final Throwable cause;
    private final LocaleMessageSourceService messageSourceService;


    @Override
    public ResponseEntity createWallet(MemberWalletDto memberWalletDto) {
        if(cause instanceof FeignException && ((FeignException) cause).status()==404){
            log.error("404 error occurred when createWallet called for inputs: "
                    +memberWalletDto.toString()+" . Error message: "
                    +cause.getLocalizedMessage());
        }else {
            log.error("Other error took place: "+cause.getLocalizedMessage());
        }

        return ResponseEntity.ok(messageSourceService.getMessage("WALLET_WS_PROBLEM"));
    }

    @Override
    public ResponseEntity getWallets(BigDecimal id) {

        if(cause instanceof FeignException && ((FeignException) cause).status()==404){
            log.error("404 error occurred when getWallets called for userID: "
                    +id+" . Error message: "
                    +cause.getLocalizedMessage());
        }else {
            log.error("Other error took place: "+cause.getLocalizedMessage());
        }

        return ResponseEntity.ok(messageSourceService.getMessage("WALLET_WS_PROBLEM"));
    }
}