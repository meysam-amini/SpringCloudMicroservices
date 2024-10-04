package com.meysam.common.customsecurity.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data@Builder
public class BackofficeRoleDTO implements Serializable {

    private String name;

    private String enKey;

    private String code;
}