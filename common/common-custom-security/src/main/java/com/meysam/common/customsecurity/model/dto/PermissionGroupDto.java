package com.meysam.common.customsecurity.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PermissionGroupDto {
    private String name;
    private String code;
    private List<PermissionDTO> subGroup = new ArrayList<>();
}
