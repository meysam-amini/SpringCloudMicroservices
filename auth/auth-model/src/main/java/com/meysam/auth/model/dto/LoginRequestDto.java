package com.meysam.auth.model.dto;

import com.meysam.auth.model.enums.OtpTarget;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Setter
@Builder@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
