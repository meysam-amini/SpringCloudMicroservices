package com.meysam.auth.model.dto;

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
}