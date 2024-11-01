package com.meysam.common.customsecurity.model.entity;

import com.meysam.common.customsecurity.model.enums.UserTypeEnum;
import com.meysam.common.model.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Table(name = "PROFILE")
@Data
@Entity
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    @SequenceGenerator(name = "profile_seq", sequenceName = "profile_seq", allocationSize = 1)
    private long id;

    @NotNull(message = "invalid username")
    @Size(max = 50)
    @Column(name = "USERNAME")
    private String username;

    @NotNull(message = "invalid password")
    @Size(max = 100)
    @Column(name = "PASSWORD")
    private String password;

    @Size(max = 50)
    @Column(name = "FIRSTNAME")
    private String firstName;

    @Size(max = 50)
    @Column(name = "LASTNAME")
    private String lastName;

    @Size(max = 50)
    @Column(name = "FATHERNAME")
    private String fatherName;


    @Column(name = "GENDER")
    private Long gender;

    @Size(max = 20)
    @Column(name = "PHONENUMBER")
    private String phoneNumber;

    @Size(max = 1000)
    @Column(name = "ADDRESS")
    private String address;

    @NotNull(message = "invalid is active")
    @Column(name = "ISACTIVE")
    private Boolean isActive;




}