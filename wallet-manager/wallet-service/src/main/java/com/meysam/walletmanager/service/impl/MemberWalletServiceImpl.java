package com.meysam.walletmanager.service.impl;

import com.meysam.common.model.entity.User;
import com.meysam.common.model.entity.UserWallet;
import com.meysam.common.utils.exception.BusinessException;
import com.meysam.common.utils.messages.LocaleMessageSourceService;
import com.meysam.users.service.api.UserService;
import com.meysam.walletmanager.dao.repository.memberwallet.UserWalletRepository;
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

    private final UserWalletRepository memberWalletRepository;
    private final UserService userService;
    private final LocaleMessageSourceService messageSourceService;

    @Transactional
    @Override
    public String generateWalletAndReturnAddress(BigDecimal userId, String unit) {

        String existingAddress = memberWalletRepository.findAddressByUserAndCoinUnit(userId,unit);
        if (existingAddress!=null){
            return existingAddress;
        }
        else {
            User user = userService.findById(userId);
            if(user==null)
                throw new BusinessException(messageSourceService.getMessage("USER_NOT_FOUND"));

            String address= UUID.randomUUID().toString();
            UserWallet memberWallet = UserWallet.builder()
                    .coinUnit(unit)
                    .User(user)
                    .address(address)
                    .build();
            memberWalletRepository.save(memberWallet);
            return address;
        }
    }

    @Override
    public List<UserWallet> getWalletsByUsername(String username) {
        User user = userService.findByUserName(username);
        if(user==null)
            throw new BusinessException(messageSourceService.getMessage("USER_NOT_FOUND"));
        return memberWalletRepository.findAllWalletsByUser(user.getId());
    }
}
