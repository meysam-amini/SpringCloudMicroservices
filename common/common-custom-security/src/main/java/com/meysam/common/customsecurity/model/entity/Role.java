package com.meysam.common.customsecurity.model.entity;

import com.meysam.common.model.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigInteger;

@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Entity
public class Role extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(name = "role_seq", sequenceName = "role_seq", allocationSize = 1)
    private long id;

    @NotNull(message = "invalid name")
    @Size(max = 50)
    @Column(name = "NAME")
    String name;

    @NotNull(message = "invalid code")
    @Size(max = 20)
    @Column(name = "CODE")
    String code;

    @NotNull(message = "invalid parent")
    @Column(name = "PARENT")
    private Long parent;


}