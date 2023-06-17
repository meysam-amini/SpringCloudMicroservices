package com.meysam.users.service.api;

import com.meysam.users.service.impl.WalletServiceFallBackFactory;
import com.meysam.common.model.dto.UserWalletDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

@FeignClient(name = "wallet-ws",fallbackFactory = WalletServiceFallBackFactory.class)
public interface WalletServiceClient {

    @GetMapping("/member-wallet/wallets/create")
    public ResponseEntity createWallet(@RequestBody @Valid UserWalletDto memberWalletDto);

    @GetMapping("/member-wallet/wallets/{memberId}")
    public ResponseEntity getWallets(@PathVariable("userId") BigDecimal userId);

}