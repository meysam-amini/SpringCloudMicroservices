package com.meysam.users.webapi.controller;

import com.meysam.common.model.dto.MemberWalletDto;
import com.meysam.common.utils.messages.LocaleMessageSourceService;
import com.meysam.users.service.api.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("member/asset")
@RequiredArgsConstructor
public class MemberAssetController {

    private final MemberService memberService;
    private final LocaleMessageSourceService messageSourceService;

    @GetMapping(value = "wallets")
//    @PreAuthorize("principal == #userId")
//    @PostAuthorize("principal == returnObject.getBody().getUserId()")
    @PreAuthorize("hasAnyAuthority('ROLE_USER_LEVEL_1')")
    public ResponseEntity getUserWallets(JwtAuthenticationToken token) {
        return ResponseEntity.ok().body(memberService.getUserWallets(token.toString(),token.getName()));
    }


    @PostMapping("create-wallet")
    @PreAuthorize("hasAnyAuthority('ROLE_USER_LEVEL_1')")
    public ResponseEntity<String> createMemberWallet(@RequestBody @Valid MemberWalletDto memberWalletDto,JwtAuthenticationToken token) {
        if(token.getName().equals(memberWalletDto.getUsername()))
            return memberService.createMemberWalletAddress("Bearer "+token.getToken().getTokenValue(), memberWalletDto);
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageSourceService.getMessage("INVALID_USERNAME"));
    }
    }
