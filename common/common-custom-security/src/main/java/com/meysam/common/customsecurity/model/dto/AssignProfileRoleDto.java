package com.meysam.common.customsecurity.model.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter@Builder
public class AssignProfileRoleDto {

    private List<String> roles;
    private String username;
}
