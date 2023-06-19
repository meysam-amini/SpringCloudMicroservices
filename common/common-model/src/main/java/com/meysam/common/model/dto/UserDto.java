package com.meysam.common.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private BigDecimal userId;
    private String username;

}
