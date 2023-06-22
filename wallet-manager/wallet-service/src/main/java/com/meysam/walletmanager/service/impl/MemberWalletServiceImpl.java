package com.meysam.walletmanager.service.impl;

import com.meysam.common.model.entity.Member;
import com.meysam.common.model.entity.MemberWallet;
import com.meysam.common.utils.exception.BusinessException;
import com.meysam.common.utils.messages.LocaleMessageSourceService;
import com.meysam.users.service.api.MemberService;
import com.meysam.walletmanager.dao.repository.memberwallet.MemberWalletRepository;
import com.meysam.walletmanager.service.api.MemberWalletService;
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
    private final MemberService userService;
    private final LocaleMessageSourceService messageSourceService;

    @Override
    public String generateWalletAndReturnAddress(BigDecimal userId, String unit) {

        try {
            String existingAddress = memberWalletRepository.findAddressByMemberAndCoinUnit(userId,unit);
            if (existingAddress!=null){
                return existingAddress;
            }
            else {
                Member user = userService.findById(userId);
                if (user == null)
                    throw new BusinessException(messageSourceService.getMessage("USER_NOT_FOUND"));

                String address = UUID.randomUUID().toString();
                MemberWallet memberWallet = MemberWallet.builder()
                        .coinUnit(unit)
                        .member(user)
                        .address(address)
                        .build();
                memberWalletRepository.save(memberWallet);
                return address;
            }
        }catch (Exception e){
            throw new BusinessException("");
        }
    }

    @Override
    public List<MemberWallet> getWalletsByUsername(String username) {
        Member user = userService.findByUserName(username);
        if(user==null)
            throw new BusinessException(messageSourceService.getMessage("USER_NOT_FOUND"));
        return memberWalletRepository.findAllWalletsByMember(user.getId());
    }
}
