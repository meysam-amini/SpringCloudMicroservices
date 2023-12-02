package com.meysam.backoffice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Table(name = "PROFILE")
@Data
@Entity
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @Column(name = "FIRSTNAME")
    String firstName;

    @Size(max = 50)
    @Column(name = "LASTNAME")
    String lastName;

    @Size(max = 50)
    @Column(name = "FATHERNAME")
    String fatherName;

    @Size(max = 50)
    @Column(name = "ORGANIZATIONNAME")
    String organizationName;

    @Size(max = 10)
    @Column(name = "GENDER")
    String gender;

    @Column(name = "NATIONALITY")
    Long nationality;

    @Size(max = 12)
    @Column(name = "NATIONALID")
    String nationalId;

    @Size(max = 20)
    @Column(name = "NATIONALCERTIFICATECODE")
    String nationalCertificateCode;

    @Size(max = 20)
    @Column(name = "NATIONALCERTIFICATESERIAL")
    String nationalCertificateSerial;

    @Size(max = 12)
    @Column(name = "FOREIGNERID")
    String foreignerId;

    @Column(name = "MARITALSTATUS")
    Long maritalStatus;

    @Column(name = "CHILDRENNUMBER")
    Long childrenNumber;

    @Column(name = "BIRTHDATE")
    LocalDate birthDate;

    @Column(name = "DEATHDATE")
    LocalDate deathDate;

    @Column(name = "FOUNDATIONDATE")
    Long foundationDate;

    @Column(name = "DEFUNCTDATE")
    Long defunctDate;

    @Column(name = "GRADUATION")
    Long graduation;

    @Column(name = "PHYSICALCONDITION")
    String physicalCondition;

    @Column(name = "CURRENTTAMINWORKSHOPCODE")
    String currentTaminWorkshopCode;

    @Column(name = "GRADUATIONLEVEL")
    String graduationLevel;

//    @Column(name = "RELIGEN")
//    Long religen;

    @Column(name = "LOCATION")
    Long location;

    @Size(max = 20)
    @Column(name = "PHONENUMBER")
    String phoneNumber;

    @Size(max = 20)
    @Column(name = "MOBILENUMBER")
    String mobileNumber;

    @Size(max = 20)
    @Column(name = "FAXNUMBER")
    String faxNumber;

    @Size(max = 10)
    @Column(name = "ZONECODE")
    String zoneCode;

    @Size(max = 10)
    @Column(name = "POSTALCODE")
    String postalCode;

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

    @NotNull(message = "invalid is active")
    @Column(name = "ISACTIVE")
    Boolean isActive;

    //    @NotNull(message= "invalid secret key")
    @Size(max = 16000)
    @Column(name = "SECRETKEY")
    String secretKey;


    @Column(name = "ACTIVEFROM")
    @Temporal(TemporalType.TIMESTAMP)
    Date activeFrom;


    @Column(name = "ACTIVETO")
    @Temporal(TemporalType.TIMESTAMP)
    Date activeTo;


    @Column(name = "CREATEDAT")
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;


    @Column(name = "CREATEDBY")
    Long createdBy;

    @Column(name = "MODIFIEDAT")
    @Temporal(TemporalType.TIMESTAMP)
    Date modifiedAt;

    @Column(name = "MODIFIEDBY")
    Long modifiedBy;

    @Column(name = "RELIGION")
    Long religion;

}