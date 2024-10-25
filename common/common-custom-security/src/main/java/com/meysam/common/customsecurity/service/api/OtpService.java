package com.meysam.common.customsecurity.service.api;

import com.meysam.common.customsecurity.model.constants.OtpConstants;
import com.meysam.common.customsecurity.model.dto.RestResponseDTO;
import org.springframework.http.ResponseEntity;

public interface OtpService {

    ResponseEntity<RestResponseDTO> sendOtp(String msg, String username, OtpConstants.OtpType otpTarget, OtpConstants.OtpOperation otpOperation, String destination);
    void validateOtpCode(String username, OtpConstants.OtpType otpTarget,OtpConstants.OtpOperation otpOperation, int enteredOtpCode);
    void removeCachedOtpCodeAndWrongOtpCount(String username, OtpConstants.OtpType otpTarget,OtpConstants.OtpOperation otpOperation);
}
