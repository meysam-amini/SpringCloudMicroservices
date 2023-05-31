package com.meysam.controller;

import com.meysam.model.dto.MemberWalletDto;
import com.meysam.service.MemberWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.LocaleMessageSourceService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("member-wallet")
public class MemberWalletController {

    private MemberWalletService memberWalletService;
    private LocaleMessageSourceService messageSourceService;

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


}
