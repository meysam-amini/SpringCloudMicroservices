package com.meysam.common.customsecurity.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter@Builder
public class AssignRolePermissionDto {

    private String permissionCode;
    private String roleCode;
}
