package com.meysam.common.customsecurity.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
public class RegisterUserDto {

    private String firstName;
    private String lastName;
    private String fatherName;
    private Integer gender;
    private String username;
    private String temporalPassword;
    private String address;
    private String phoneNumber;
    private String email;
    private String nationalId;
    private Long nationality;
    private String organizationName;
    private String birthDate;
    private String postalCode;
    private String zoneCode;
    //roles codes
    private List<String> roles= new ArrayList<>();
    //permissions codes
    private List<String> permissions= new ArrayList<>();

}
