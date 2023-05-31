package com.meysam.model.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberWalletDto {

    @NotNull(message = "memberId of memberWallet cannot be null")
    private BigDecimal memberId;

    @NotNull(message = "coinUnit of memberWallet cannot be null")
    private String coinUnit;

    private String address;
}
