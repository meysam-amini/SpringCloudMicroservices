package com.meysam.service;

import java.math.BigDecimal;

public interface MemberWalletService {

    public String generateWalletAndReturnAddress(BigDecimal memberId, String unit);

}
