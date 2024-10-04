package com.meysam.common.customsecurity.model.dto;


import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter@Builder
public class ResetPasswordDTO implements Serializable {

    private String oldPassword;
    private String newPassword;
    private Integer otpCode;

}
