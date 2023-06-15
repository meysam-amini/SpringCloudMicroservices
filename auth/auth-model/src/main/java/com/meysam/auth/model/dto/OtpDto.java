package com.meysam.auth.model.dto;

import com.meysam.auth.model.enums.OtpTarget;
import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Builder@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OtpDto {

    @NotNull
    private long otpCode;

}
