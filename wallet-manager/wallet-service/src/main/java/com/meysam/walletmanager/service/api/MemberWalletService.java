package com.meysam.walletmanager.service.api;

import com.meysam.common.model.entity.UserWallet;

import java.math.BigDecimal;
import java.util.List;

public interface MemberWalletService {

    String generateWalletAndReturnAddress(BigDecimal userId, String unit);

    List<UserWallet> getWalletsByUsername(String username);

}
