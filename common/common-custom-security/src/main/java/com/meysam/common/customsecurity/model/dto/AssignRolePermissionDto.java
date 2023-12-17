package com.meysam.common.customsecurity.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter@Builder
public class AssignRolePermissionDto {

    @NotNull(message = "invalid permission code")
    private String permissionCode;
    @NotNull(message = "invalid role code")
    private String roleCode;
}
