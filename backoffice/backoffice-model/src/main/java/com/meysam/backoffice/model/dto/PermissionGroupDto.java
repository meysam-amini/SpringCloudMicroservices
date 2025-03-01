package com.meysam.backoffice.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PermissionGroupDto {
    private String name;
    private List<String> subGroups = new ArrayList<>();
}
