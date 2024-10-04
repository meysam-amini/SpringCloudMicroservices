package com.meysam.common.customsecurity.model.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

    private Long id;
    private String nationalCode;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String foreignerCode;
    private String birthDate;
    private Long gender;
//    private String nationality;
    private Long religion;
    private Long maritalStatus;
    private Long childrenNumber;
    private String graduationLevel;
    private String physicalCondition;
    private Long militaryStatus;
    private String mobileNumber;
    private String phoneNumber;
    private String faxNumber;
    private String email;
    private Long location;
    private String locationDesc;
    private Long parentLocation;
    private String parentLocationDesc;
    private String postalCode;
    private String address;
    private Long militaryServiceStatus;
    private Boolean isPermittedToReceiveByFax;
    private Boolean isPermittedToReceiveByEmail;
    private String zoneCode;

    private Long nationality;

    private String nationalityDescription;

    private String maritalStatusDescription;

    private Long graduation;

    private String organizationName;

    private String religionDesc;

    private String genderDesc;

    private String nationalCertificateCode;

    private String militaryServicesStatusDesc;

}
