package com.meysam.common.customsecurity.model.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter@Setter@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolesPermissionsDTO implements Serializable {

    private RoleDTO role;
    private List<PermissionDTO> permissions;
}