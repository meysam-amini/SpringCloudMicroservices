package com.meysam.auth.model.dto;

import lombok.*;

import jakarta.validation.constraints.NotNull;

@Setter
@Builder@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakLoginRequestDto {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String clientId;
    @NotNull
    private String clientSecret;
    @NotNull
    private String grantType;
}
