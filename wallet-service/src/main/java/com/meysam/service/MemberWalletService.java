package com.meysam.service;

import com.meysam.model.entity.MemberWallet;

import java.math.BigDecimal;
import java.util.List;

public interface MemberWalletService {

    public String generateWalletAndReturnAddress(BigDecimal memberId, String unit);

    public List<MemberWallet> getWalletsByMember(BigDecimal memberId);

}
