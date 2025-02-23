package com.meysam.common.customsecurity.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private String enKey;

    private int parent;


}