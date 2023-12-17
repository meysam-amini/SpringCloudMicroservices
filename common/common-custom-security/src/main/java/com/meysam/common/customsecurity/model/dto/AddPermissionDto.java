package com.meysam.common.customsecurity.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter@Builder
public class AddPermissionDto {

    @NotNull(message = "invalid name")
    @Size(max = 50)
    private String name;

    @NotNull(message = "invalid code")
    @Size(max = 20)
    private String code;
}
