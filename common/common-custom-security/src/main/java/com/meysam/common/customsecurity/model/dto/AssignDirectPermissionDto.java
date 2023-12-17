package com.meysam.common.customsecurity.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter@Builder
public class AssignDirectPermissionDto {

    private String permissionCode;
    private String username;
}
