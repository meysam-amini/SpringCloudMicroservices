package com.meysam.common.model.dto;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberWalletDto {

    private String username;

    @NotBlank(message = "{member.wallet.coin.unit.notnull}")
    private String coinUnit;

    private String address;
}
