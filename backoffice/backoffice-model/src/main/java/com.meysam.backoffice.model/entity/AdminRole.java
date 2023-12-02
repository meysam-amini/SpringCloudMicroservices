package com.meysam.backoffice.model.entity;

import com.meysam.common.model.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Table(name = "PROFILEROLE")
@Data
@Entity
public class AdminRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "invalid role")
    @Column(name = "ROLE")
    Long role;

    @NotNull(message = "invalid profile")
    @Column(name = "PROFILE")
    Long admin;
}