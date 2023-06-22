package com.meysam.walletmanager.service.api;

import com.meysam.common.model.dto.MemberWalletDto;
import com.meysam.common.model.entity.MemberWallet;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface MemberWalletService {

    ResponseEntity<String> generateWalletAndReturnAddress(BigDecimal userId, String unit);

    ResponseEntity<List<MemberWalletDto>> getWalletsByUsername(String username);

}
