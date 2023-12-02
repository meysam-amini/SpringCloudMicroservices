package com.meysam.common.customsecurity.model.entity;

import com.meysam.common.model.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Table(name = "PROFILEROLE")
@Data
@Entity
public class AdminRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private BigInteger id;

    @NotNull(message = "invalid role")
    @Column(name = "ROLE")
    BigInteger role;

    @NotNull(message = "invalid profile")
    @Column(name = "PROFILE")
    BigInteger admin;
}