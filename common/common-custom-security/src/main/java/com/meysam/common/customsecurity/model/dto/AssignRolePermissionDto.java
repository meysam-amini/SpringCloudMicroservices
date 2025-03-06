package com.meysam.common.customsecurity.model.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter@Builder
public class AssignRolePermissionDto {

    private List<String> permissionCodes;
    private Long roleId;
}
