package com.meysam.walletmanager.webapi.controller.memberwallet;

import com.meysam.common.utils.exception.BusinessException;
import com.meysam.common.utils.messages.LocaleMessageSourceService;
import com.meysam.common.model.dto.MemberWalletDto;
import com.meysam.walletmanager.service.api.MemberWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("member-wallet")
public class MemberWalletController {

    private final MemberWalletService memberWalletService;
    private final LocaleMessageSourceService messageSourceService;

    @PostMapping("create")
    public ResponseEntity<String> generateOrReturnAddress(@RequestBody @Valid MemberWalletDto memberWalletDto) {
       return memberWalletService.generateWalletAndReturnAddress(memberWalletDto.getMemberId(),memberWalletDto.getCoinUnit());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER_LEVEL_1')")
    @GetMapping("wallets")
    public ResponseEntity<List<MemberWalletDto>> getWallets(JwtAuthenticationToken jwtAuthenticationToken) {
        //user-name-attribute=preferred_username:
        return memberWalletService.getWalletsByUsername(jwtAuthenticationToken.getName());
    }


    //get wallets scop should be added on Keycloak
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    @GetMapping("wallets/{username}")
    public ResponseEntity<List<MemberWalletDto>> getWalletsByAuthCLients(@PathVariable("username") String username) {
        return memberWalletService.getWalletsByUsername(username);
    }


}
