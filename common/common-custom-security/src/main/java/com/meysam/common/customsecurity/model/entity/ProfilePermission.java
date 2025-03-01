package com.meysam.common.customsecurity.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Table(name = "PROFILEPERMISSION")
@Data
@Entity
public class ProfilePermission  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_permission_seq")
    @SequenceGenerator(name = "profile_permission_seq", sequenceName = "profile_permission_seq", allocationSize = 1)
    private Long id;

    @NotNull(message = "invalid permission")
    @Column(name = "PERMISSION")
    Long permission;

    @NotNull(message = "invalid profile")
    @Column(name = "PROFILE")
    Long profile;

    @Column(name = "EXPIREDATE")
    @Temporal(TemporalType.TIMESTAMP)
    Date expireDate;

    //    @NotNull(message = "invalid date time")
    @Column(name = "CREATEDAT")
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

    //    @NotNull
    @Column(name = "CREATEDBY")
    Long createdBy;

    @Column(name = "MODIFIEDAT")
    @Temporal(TemporalType.TIMESTAMP)
    Date modifiedAt;

    @Column(name = "MODIFIEDBY")
    Long modifiedBy;


}