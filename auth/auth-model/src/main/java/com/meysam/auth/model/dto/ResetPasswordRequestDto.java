package com.meysam.auth.model.dto;

import com.meysam.auth.model.enums.OtpTarget;
import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Builder@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequestDto {

    @NotNull
    private String username;
    @NotNull
    private String newPassword;
    @NotNull
    private String oldPassword;

    private OtpTarget otpTarget;
}
