package com.meysam.auth.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Setter
@Builder@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakRegisterCredentialsDto implements Serializable {

    private String type="password";
    @NotBlank
    private String value;
    private boolean temporary = false;

}
