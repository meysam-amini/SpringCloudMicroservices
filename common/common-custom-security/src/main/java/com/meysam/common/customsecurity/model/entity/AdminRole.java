package com.meysam.common.customsecurity.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Table(name = "PROFILEROLE")
@Data
@Entity
public class AdminRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "invalid role")
    @Column(name = "ROLE")
    Long role;

    @NotNull(message = "invalid profile")
    @Column(name = "PROFILE")
    Long profile;

    @NotNull(message = "invalid is active")
    @Column(name = "ISACTIVE")
    Boolean isActive;

    @NotNull(message = "invalid date time")
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