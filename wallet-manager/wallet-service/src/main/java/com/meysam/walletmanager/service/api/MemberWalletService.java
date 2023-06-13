package com.meysam.walletmanager.service.api;

import com.meysam.walletmanager.model.entity.MemberWallet;

import java.math.BigDecimal;
import java.util.List;

public interface MemberWalletService {

    String generateWalletAndReturnAddress(BigDecimal memberId, String unit);

    List<MemberWallet> getWalletsByMember(BigDecimal memberId);

}
