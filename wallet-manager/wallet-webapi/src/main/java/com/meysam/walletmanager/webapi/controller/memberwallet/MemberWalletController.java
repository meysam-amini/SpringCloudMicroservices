package com.meysam.walletmanager.webapi.controller.memberwallet;

import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.model.dto.MemberWalletDto;
import com.meysam.walletmanager.service.api.MemberWalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("member-wallet")
public class MemberWalletController {

    private final MemberWalletService memberWalletService;
    private final LocaleMessageSourceService messageSourceService;


    @PostMapping("create")
    @PreAuthorize("hasAuthority('ROLE_USER_LEVEL_1')")
    public ResponseEntity<String> generateOrReturnAddress(@RequestBody @Valid MemberWalletDto memberWalletDto,JwtAuthenticationToken jwtAuthenticationToken) {
        if (memberWalletDto.getUsername().equals(jwtAuthenticationToken.getName()))
            return memberWalletService.generateWalletAndReturnAddress(memberWalletDto.getUsername(),memberWalletDto.getCoinUnit());
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageSourceService.getMessage("INVALID_USERNAME"));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER_LEVEL_1')")
    @GetMapping("wallets")
    public List<MemberWalletDto> getWallets(JwtAuthenticationToken jwtAuthenticationToken) {
        return memberWalletService.getWalletsByUsername(jwtAuthenticationToken.getName());
    }


    @PreAuthorize("hasAuthority('SCOPE_member_info')")
    @GetMapping("wallets/{username}")
    public List<MemberWalletDto> getWalletsByAuthClients(@PathVariable("username") String username) {
        return memberWalletService.getWalletsByUsername(username);
    }


}
