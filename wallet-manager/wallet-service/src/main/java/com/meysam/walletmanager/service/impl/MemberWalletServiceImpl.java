package com.meysam.walletmanager.service.impl;

import com.meysam.common.model.dto.MemberWalletDto;
import com.meysam.common.model.entity.Member;
import com.meysam.common.model.entity.MemberWallet;
import com.meysam.common.utils.exception.BusinessException;
import com.meysam.common.utils.messages.LocaleMessageSourceService;
import com.meysam.users.service.api.MemberService;
import com.meysam.walletmanager.dao.repository.memberwallet.MemberWalletRepository;
import com.meysam.walletmanager.service.api.MemberWalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberWalletServiceImpl implements MemberWalletService {

    private final MemberWalletRepository memberWalletRepository;
    private final MemberService userService;
    private final LocaleMessageSourceService messageSourceService;

    @Override
    public ResponseEntity<String> generateWalletAndReturnAddress(BigDecimal userId, String unit) {

        try {
            String existingAddress = memberWalletRepository.findAddressByMemberAndCoinUnit(userId,unit);
            if (existingAddress!=null){
                return ResponseEntity.ok(existingAddress);
            }
            else {
                Member user = userService.findById(userId);
                if (user == null)
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(messageSourceService.getMessage("USER_NOT_FOUND"));

                String address = UUID.randomUUID().toString();
                MemberWallet memberWallet = MemberWallet.builder()
                        .coinUnit(unit)
                        .member(user)
                        .address(address)
                        .build();
                memberWalletRepository.save(memberWallet);
                return ResponseEntity.status(HttpStatus.CREATED).body(address);
            }
        }catch (Exception e){
            log.error("DB connection error on generateWalletAndReturnAddress method at time :{} , exception:{}",System.currentTimeMillis(),e);
            throw new BusinessException(messageSourceService.getMessage("REQUEST_FAILED"));
        }
    }

    @Override
    public ResponseEntity<List<MemberWalletDto>> getWalletsByUsername(String username) {
        Member user = userService.findByUserName(username);
        if(user==null)
            throw new BusinessException(messageSourceService.getMessage("USER_NOT_FOUND"));
        return ResponseEntity.ok(memberWalletRepository.findAllWalletsByMember(user.getId()));
    }
}
