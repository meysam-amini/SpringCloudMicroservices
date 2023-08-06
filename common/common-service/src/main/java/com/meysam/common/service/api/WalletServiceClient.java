package com.meysam.common.service.api;

import com.meysam.common.model.dto.MemberWalletDto;
import com.meysam.common.service.impl.WalletServiceFallBackFactory;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "wallet-ws",fallbackFactory = WalletServiceFallBackFactory.class)
public interface WalletServiceClient {

    @GetMapping("/member-wallet/create")
    ResponseEntity<String> createWallet(@RequestHeader("Authorization") String token, @RequestBody @Valid MemberWalletDto memberWalletDto);

    @GetMapping("/member-wallet/wallets/{username}")
    List<MemberWalletDto> getWallets(@RequestHeader("Authorization") String token,@PathVariable("username") String username);

}