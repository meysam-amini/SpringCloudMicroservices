package com.meysam.common.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Builder@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientLoginRequestDto {

    @NotBlank
    private String clientId;
    @NotBlank
    private String clientSecret;

}
