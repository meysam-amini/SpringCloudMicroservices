package com.meysam.common.customsecurity.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter@Builder
public class AssignRolePermissionDto {


    private List<String> permissions;
    private String roleCode;
}
