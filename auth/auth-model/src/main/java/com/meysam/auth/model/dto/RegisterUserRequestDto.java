package com.meysam.auth.model.dto;

import com.meysam.auth.model.enums.OtpTarget;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Setter
@Builder@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private boolean enabled;


}
