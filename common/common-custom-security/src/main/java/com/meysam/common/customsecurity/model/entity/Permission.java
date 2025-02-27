package com.meysam.common.customsecurity.model.entity;

import com.meysam.common.model.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Table(name = "PERMISSION")
@Data
@Entity
public class Permission extends BaseEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_seq")
    @SequenceGenerator(name = "permission_seq", sequenceName = "permission_seq", allocationSize = 1)
    private long id;

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

    @NotNull(message = "invalid parent")
    @Column(name = "PARENT")
    private int parent;
}