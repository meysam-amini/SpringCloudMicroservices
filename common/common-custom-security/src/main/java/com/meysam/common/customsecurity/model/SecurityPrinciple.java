package com.meysam.common.customsecurity.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class SecurityPrinciple implements Serializable {
    private List<String> roles;
    private List<String> permissions;
    private String username;
    private Long profileId;
}
