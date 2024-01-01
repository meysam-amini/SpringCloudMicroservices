package com.meysam.common.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Builder
public class CaptchaDto {
    private String captchaImage;
    private String captchaId;
}
