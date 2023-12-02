package com.meysam.backoffice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Table(name = "ROLE")
@Data
@Entity
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "invalid name")
    @Size(max = 50)
    @Column(name = "NAME")
    String name;

    @NotNull(message = "invalid code")
    @Size(max = 20)
    @Column(name = "CODE")
    String code;

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