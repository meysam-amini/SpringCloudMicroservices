package com.meysam.common.customsecurity.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Table(name = "PERMISSION")
@Data
@Entity
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private BigInteger id;

    @NotNull(message = "invalid name")
    @Size(max = 50)
    @Column(name = "NAME")
    String name;

    @NotNull(message = "invalid code")
    @Size(max = 20)
    @Column(name = "CODE")
    String code;

}