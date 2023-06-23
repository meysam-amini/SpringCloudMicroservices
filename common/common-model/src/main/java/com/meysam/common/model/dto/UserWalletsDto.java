package com.meysam.common.model.dto;

import lombok.*;

import java.util.List;

@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserWalletsDto {

    private UserDto user;
    private List<MemberWalletDto> wallets;
}
