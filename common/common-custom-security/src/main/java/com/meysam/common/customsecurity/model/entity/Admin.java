package com.meysam.common.customsecurity.model.entity;

import com.meysam.common.model.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Table(name = "ADMIN")
@Data
@Entity
public class Admin extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private BigInteger id;

    @Size(max = 50)
    @Column(name = "FIRSTNAME")
    String firstName;

    @Size(max = 50)
    @Column(name = "LASTNAME")
    String lastName;

    @Size(max = 50)
    @Column(name = "ORGANIZATIONNAME")
    String organizationName;

    @Size(max = 10)
    @Column(name = "GENDER")
    String gender;

    @Size(max = 12)
    @Column(name = "NATIONALID")
    String nationalId;

    @Size(max = 20)
    @Column(name = "MOBILENUMBER")
    String mobileNumber;

    @Size(max = 1000)
    @Column(name = "ADDRESS")
    String address;

    @NotNull(message = "invalid username")
    @Size(max = 50)
    @Column(name = "USERNAME")
    String username;

    @NotNull(message = "invalid password")
    @Size(max = 50)
    @Column(name = "PASSWORD")
    String password;

    @Size(max = 16000)
    @Column(name = "SECRETKEY")
    String secretKey;


    @Column(name = "ACTIVEFROM")
    @Temporal(TemporalType.TIMESTAMP)
    Date activeFrom;


    @Column(name = "ACTIVETO")
    @Temporal(TemporalType.TIMESTAMP)
    Date activeTo;


}