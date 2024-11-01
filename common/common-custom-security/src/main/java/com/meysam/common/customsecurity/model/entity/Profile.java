package com.meysam.common.customsecurity.model.entity;

import com.meysam.common.customsecurity.model.enums.UserTypeEnum;
import com.meysam.common.model.entity.BaseEntity;
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
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    @SequenceGenerator(name = "profile_seq", sequenceName = "profile_seq", allocationSize = 1)
    private long id;

    @Size(max = 50)
    @Column(name = "FIRSTNAME")
    private String firstName;

    @Size(max = 50)
    @Column(name = "LASTNAME")
    private String lastName;

    @Size(max = 50)
    @Column(name = "FATHERNAME")
    private String fatherName;

    @Column(name = "IDNO")
    private String idNo;

    @Size(max = 50)
    @Column(name = "ORGANIZATIONNAME")
    private String organizationName;

    @Column(name = "GENDER")
    private Long gender;

    @Column(name = "NATIONALITY")
    private Long nationality;

    @Size(max = 12)
    @Column(name = "NATIONALID")
    private String nationalId;

    @Size(max = 20)
    @Column(name = "NATIONALCERTIFICATECODE")
    private String nationalCertificateCode;

    @Size(max = 20)
    @Column(name = "NATIONALCERTIFICATESERIAL")
    private String nationalCertificateSerial;

    @Size(max = 12)
    @Column(name = "FOREIGNERID")
    private String foreignerId;

    @Column(name = "MARITALSTATUS")
    private Long maritalStatus;

    @Column(name = "CHILDRENNUMBER")
    private Long childrenNumber;

    @Column(name = "BIRTHDATE")
    private String birthDate;

    @Column(name = "DEATHDATE")
    private LocalDate deathDate;

    @Column(name = "FOUNDATIONDATE")
    private Long foundationDate;

    @Column(name = "DEFUNCTDATE")
    private Long defunctDate;

    @Column(name = "GRADUATION")
    private Long graduation;

    @Column(name = "PHYSICALCONDITION")
    private String physicalCondition;

    @Column(name = "CURRENTTAMINWORKSHOPCODE")
    private String currentTaminWorkshopCode;

    @Column(name = "GRADUATIONLEVEL")
    private String graduationLevel;

//    @Column(name = "RELIGEN")
//    Long religen;

    @Column(name = "LOCATION")
    private Long location;

    @Column(name = "PARENTLOCATION")
    private Long parentLocation;

    @Size(max = 20)
    @Column(name = "PHONENUMBER")
    private String phoneNumber;

    @Size(max = 20)
    @Column(name = "MOBILENUMBER")
    private String mobileNumber;

    @Size(max = 20)
    @Column(name = "FAXNUMBER")
    private String faxNumber;

    @Size(max = 10)
    @Column(name = "ZONECODE")
    private String zoneCode;

    @Size(max = 10)
    @Column(name = "POSTALCODE")
    private String postalCode;

    @Size(max = 1000)
    @Column(name = "ADDRESS")
    private String address;

    @NotNull(message = "invalid username")
    @Size(max = 50)
    @Column(name = "USERNAME")
    private String username;

    @NotNull(message = "invalid password")
    @Size(max = 100)
    @Column(name = "PASSWORD")
    private String password;

    @NotNull(message = "invalid is active")
    @Column(name = "ISACTIVE")
    private Boolean isActive;

    @Column(name = "ACTIVEFROM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activeFrom;


    @Column(name = "ACTIVETO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activeTo;


    @Column(name = "CREATEDAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "CREATEDBY")
    private Long createdBy;


    @Column(name = "ISPERMITTEDTORECEIVEBYEMAIL")
    private Boolean isPermittedToReceiveByEmail;

    @Column(name = "ISPERMITTEDTORECEIVEBYFAX")
    private Boolean isPermittedToReceiveByFax;

    @Column(name = "MILITARYSERVICESTATUS")
    private Long militaryServiceStatus;

    @Column(name = "MODIFIEDAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @Column(name = "MODIFIEDBY")
    private Long modifiedBy;

//    @Column(name = "USERTYPE")
//    private UserTypeEnum userType;

}