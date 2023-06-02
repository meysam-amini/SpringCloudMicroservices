package com.meysam.model.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberWalletDto {

    @NotNull(message = "{member.wallet.member.id.notnull}")
    private BigDecimal memberId;

    @NotBlank(message = "{member.wallet.coin.unit.notnull}")
    private String coinUnit;

    private String address;
}
