package com.meysam.common.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Setter
@Builder@Getter@AllArgsConstructor@NoArgsConstructor
public class AdminLoginResponseDto {

    private String token;
}
