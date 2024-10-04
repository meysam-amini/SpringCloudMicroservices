package com.meysam.common.model.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Table(name = "PERMISSION")
@Data
@Entity
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "invalid name")
    @Size(max = 100)
    @Column(name = "NAME")
    private String name;

    @NotNull(message = "invalid enKey")
    @Size(max = 100)
    @Column(name = "ENKEY")
    private String enKey;

    @NotNull(message = "invalid code")
    @Size(max = 20)
    @Column(name = "CODE")
    private String code;

    @NotNull(message = "invalid general permission")
    @Column(name = "GENERALPERMISSION")
    private Boolean generalPermission;

}
