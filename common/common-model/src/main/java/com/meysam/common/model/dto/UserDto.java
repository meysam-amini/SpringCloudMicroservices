package com.meysam.common.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private BigDecimal id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;

}
