package com.meysam.walletmanager.service.api;

import com.meysam.common.model.dto.MemberWalletDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberWalletService {

    ResponseEntity<String> generateWalletAndReturnAddress(String username, String unit);

    ResponseEntity<List<MemberWalletDto>> getWalletsByUsername(String username);

}
