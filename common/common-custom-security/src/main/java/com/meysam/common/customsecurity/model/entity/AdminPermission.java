package com.meysam.common.customsecurity.model.entity;

import com.meysam.common.model.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Table(name = "ADMINPERMISSION")
@Data
@Entity
public class AdminPermission extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "invalid permission")
    @Column(name = "PERMISSION")
    Long permission;

    @NotNull(message = "invalid profile")
    @Column(name = "admin")
    Long admin;


}