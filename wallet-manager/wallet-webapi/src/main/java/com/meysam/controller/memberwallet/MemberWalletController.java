package com.meysam.controller.memberwallet;

import com.meysam.util.messages.LocaleMessageSourceService;
import com.meysam.walletmanager.model.dto.MemberWalletDto;
import com.meysam.walletservice.service.api.MemberWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

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
                            .generateWalletAndReturnAddress(memberWalletDto.getMemberId(), memberWalletDto.getCoinUnit()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageSourceService.getMessage("REQUEST_FAILED"));
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER_LEVEL_1')")
    @GetMapping("wallets/{memberId}")
    public ResponseEntity getWallets(@PathVariable("memberId")BigDecimal memberId){
        return ResponseEntity.ok(memberWalletService.getWalletsByMember(memberId));
    }


}
