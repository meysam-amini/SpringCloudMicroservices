package com.meysam.auth.model.dto;

import lombok.*;

@Setter
@Builder@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private Long refreshExpiresIn;
    private String tokenType;
}
