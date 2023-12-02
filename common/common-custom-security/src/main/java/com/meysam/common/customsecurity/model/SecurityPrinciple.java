package com.meysam.common.customsecurity.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
public class SecurityPrinciple {
    private List<String> roles;
    private List<String> permissions;
    private String username;
    private BigInteger adminId;
}
