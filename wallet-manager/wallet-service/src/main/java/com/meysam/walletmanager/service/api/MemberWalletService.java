package com.meysam.walletmanager.service.api;

import com.meysam.common.model.entity.MemberWallet;

import java.math.BigDecimal;
import java.util.List;

public interface MemberWalletService {

    String generateWalletAndReturnAddress(BigDecimal userId, String unit);

    List<MemberWallet> getWalletsByUsername(String username);

}
