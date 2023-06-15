package com.meysam.auth.model.dto;

import com.meysam.auth.model.enums.OtpTarget;
import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Builder@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    @NotNull
    private String username;
    @NotNull
    private String password;

    private OtpTarget otpTarget;
}
