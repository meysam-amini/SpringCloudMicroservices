package com.meysam.common.customsecurity.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter@Builder
public class AssignDirectPermissionDto {

    private List<String> permissions;
    private String username;
}
