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

@RequiredArgsConstructor
@RestController
@RequestMapping("member-wallet")
public class MemberWalletController {

    private final MemberWalletService memberWalletService;
    private final LocaleMessageSourceService messageSourceService;

    @PostMapping("create")
    public ResponseEntity generateOrReturnAddress(@RequestBody @Valid MemberWalletDto memberWalletDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(memberWalletService
                            .generateWalletAndReturnAddress(memberWalletDto.getUserId(), memberWalletDto.getCoinUnit()));
        } catch (BusinessException be) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(be.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageSourceService.getMessage("REQUEST_FAILED"));
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER_LEVEL_1')")
    @GetMapping("wallets")
    public ResponseEntity getWallets(JwtAuthenticationToken jwtAuthenticationToken) {
        //user-name-attribute=preferred_username:
        return ResponseEntity.ok(memberWalletService.getWalletsByUsername(jwtAuthenticationToken.getName()));
    }


    //get wallets scop should be added on Keycloak
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    @GetMapping("wallets/{username}")
    public ResponseEntity getWalletsByAuthCLients(@PathVariable("username") String username) {
        return ResponseEntity.ok(memberWalletService.getWalletsByUsername(username));
    }


}
