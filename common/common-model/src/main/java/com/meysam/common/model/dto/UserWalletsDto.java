package com.meysam.common.model.dto;

import com.meysam.common.model.entity.User;
import lombok.*;

import java.util.List;

@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserWalletsDto {

    private UserDto userDto;
    private List<UserWalletDto> wallets;
}
