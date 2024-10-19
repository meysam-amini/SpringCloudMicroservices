package com.meysam.common.model.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Table(name = "ROLE")
@Data
@Entity
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "invalid name")
    @Size(max = 50)
    @Column(name = "NAME")
    private String name;

    @NotNull(message = "invalid enKey")
    @Size(max = 50)
    @Column(name = "ENKEY")
    private String enKey;

    @NotNull(message = "invalid code")
    @Size(max = 20)
    @Column(name = "CODE")
    private String code;

    //    @NotNull(message = "invalid date time")
    @Column(name = "CREATEDAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    //    @NotNull
    @Column(name = "CREATEDBY")
    private Long createdBy;

    @Column(name = "MODIFIEDAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @Column(name = "MODIFIEDBY")
    private Long modifiedBy;

}
