package com.meysam.walletservice.service.impl;

import com.meysam.walletmanager.model.entity.MemberWallet;
import com.meysam.walletservice.service.api.MemberWalletService;
import com.meysam.walletservice.repository.memberwallet.MemberWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberWalletServiceImpl implements MemberWalletService {

    private final MemberWalletRepository memberWalletRepository;

    @Transactional
    @Override
    public String generateWalletAndReturnAddress(BigDecimal memberId, String unit) {

        String existingAddress = memberWalletRepository.findAddressByMemberIdAndCoinUnit(memberId,unit);
        if (existingAddress!=null){
            return existingAddress;
        }
        else {
            String address= UUID.randomUUID().toString();
            MemberWallet memberWallet = MemberWallet.builder()
                    .coinUnit(unit)
                    .memberId(memberId)
                    .address(address)
                    .build();
            memberWalletRepository.save(memberWallet);
            return address;
        }
    }

    @Override
    public List<MemberWallet> getWalletsByMember(BigDecimal memberId) {
        return memberWalletRepository.findAllWalletsByMemberId(memberId);
    }
}
