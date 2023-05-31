package com.meysam.controller;

import com.meysam.model.dto.MemberWalletDto;
import com.meysam.service.MemberWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import util.LocaleMessageSourceService;

import javax.validation.Valid;
import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("member-wallet")
public class MemberWalletController {

    private final MemberWalletService memberWalletService;
    private final LocaleMessageSourceService messageSourceService;

    @PostMapping("create")
    public ResponseEntity generateOrReturnAddress(@Valid MemberWalletDto memberWalletDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(memberWalletService
                            .generateWalletAndReturnAddress(memberWalletDto.getMemberId(), memberWalletDto.getCoinUnit()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageSourceService.getMessage("REQUEST_FAILED"));
        }
    }

    @GetMapping("wallets/{memberId}")
    public ResponseEntity getWallets(@PathVariable("memberId")BigDecimal memberId){
        return ResponseEntity.ok(memberWalletService.getWalletsByMember(memberId));
    }


}
