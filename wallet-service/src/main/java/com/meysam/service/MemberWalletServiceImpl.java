package com.meysam.service;

import com.meysam.model.MemberWallet;
import com.meysam.repository.MemberWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberWalletServiceImpl implements MemberWalletService{

    private MemberWalletRepository memberWalletRepository;

    @Transactional
    @Override
    public String generateWalletAndReturnAddress(BigDecimal memberId, String unit) {

        String existingAddress = memberWalletRepository.returnAddressByMemberIdAndCoinUnit(memberId,unit);
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
}
