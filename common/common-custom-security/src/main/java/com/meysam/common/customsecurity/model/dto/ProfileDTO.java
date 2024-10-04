package com.meysam.common.customsecurity.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.meysam.common.customsecurity.model.enums.UserTypeEnum;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Size(max = 50)
    private String fatherName;

    private String idNo;

    @Size(max = 50)
    private String organizationName;

    private Long gender;
    private String genderDesc;

    @Size(max = 50)
    private Long nationality;
    private String nationalityDesc;

    @Size(max = 12)
    private String nationalId;

    @Size(max = 20)
    private String nationalCertificateCode;

    @Size(max = 20)
    private String nationalCertificateSerial;

    @Size(max = 12)
    private String foreignerId;

    private Long maritalStatus;
    private String maritalStatusDesc;

    private Long childrenNumber;

    private Long graduation;

    private String graduationLevel;

    private Long religion;
    private String religionDesc;

    private Long location;
    private Long parentLocation;

    @Size(max = 20)
    private String phoneNumber;

    @Size(max = 20)
    private String mobileNumber;

    @Size(max = 20)
    private String faxNumber;

    private String birthDate;

    @Size(max = 10)
    private String zoneCode;

    @Size(max = 10)
    private String postalCode;

    @Size(max = 1000)
    private String address;

    private List<String> groups;

//    private Long city;
//    private Long province;
    private String email;
    private String username;
    private Boolean isActive;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date activeFrom;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date createdAt;
    private Long createdBy;

    private List<String> permissions;
    private List<RoleDTO> roles;

    private Boolean isPermittedToReceiveByEmail;

    private Boolean isPermittedToReceiveByFax;

    private String physicalCondition;

    private Long militaryServiceStatus;
    private String militaryServiceStatusDesc;
    private UserTypeEnum userType;



}
