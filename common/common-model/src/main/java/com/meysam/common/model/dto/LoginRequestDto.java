package com.meysam.common.model.dto;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Setter
@Builder@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
