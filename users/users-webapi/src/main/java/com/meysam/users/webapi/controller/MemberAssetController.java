package com.meysam.users.webapi.controller;

import com.meysam.users.service.api.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("member/asset")
@RequiredArgsConstructor
public class MemberAssetController {

    private final MemberService memberService;

    @GetMapping(value = "wallets")
//    @PreAuthorize("principal == #userId")
//    @PostAuthorize("principal == returnObject.getBody().getUserId()")
    @PreAuthorize("hasAnyAuthority('ROLE_USER_LEVEL_1')")
    public ResponseEntity getUserWallets(JwtAuthenticationToken token) {
        return ResponseEntity.ok().body(memberService.getUserWallets(token.getName()));
    }
}