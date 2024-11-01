package com.meysam.common.customsecurity.model.entity;

import com.meysam.common.model.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.math.BigInteger;

@Table(name = "ROLEPERMISSION")
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Entity
public class RolePermission extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_permission_seq")
    @SequenceGenerator(name = "role_permission_seq", sequenceName = "role_permission_seq", allocationSize = 1)
    private long id;

    @NotNull(message = "invalid role")
    @Column(name = "ROLE")
    Long role;

    @NotNull(message = "invalid permission")
    @Column(name = "PERMISSION")
    Long permission;
}