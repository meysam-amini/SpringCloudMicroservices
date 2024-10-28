package com.meysam.common.customsecurity.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileQueryModel {
    private Long id;
    private String firstName;
    private String lastName;
    private Long gender;
    private Long nationality;
    private String nationalId;
    private String foreignerId;
    private Long maritalStatus;
    private Long religion;
    private Long location;
    private Long parentLocation;
    private String phoneNumber;
    private String mobileNumber;
    private String address;
    private String email;
    private String username;
    private Long militaryServiceStatus;


    //page fields:
    private int pageNumber;
    private int pageSize;
    private String sort;
    private Sort.Direction direction;

    @JsonIgnore
    public List<BooleanExpression> getBooleanExpressions() {
        List<BooleanExpression> booleanExpressionList = new ArrayList<>();
        if (Objects.nonNull(id))
            booleanExpressionList.add(QProfile.profile.id.eq(id));
        if (Objects.nonNull(gender))
            booleanExpressionList.add(QProfile.profile.gender.eq(gender));
        if (Objects.nonNull(religion))
            booleanExpressionList.add(QProfile.profile.religion.eq(religion));
        if (Objects.nonNull(nationality))
            booleanExpressionList.add(QProfile.profile.nationality.eq(nationality));
        if (Objects.nonNull(maritalStatus))
            booleanExpressionList.add(QProfile.profile.maritalStatus.eq(maritalStatus));
        if (Objects.nonNull(location))
            booleanExpressionList.add(QProfile.profile.location.eq(location));
        if (Objects.nonNull(parentLocation))
            booleanExpressionList.add(QProfile.profile.parentLocation.eq(parentLocation));
        if (Objects.nonNull(militaryServiceStatus))
            booleanExpressionList.add(QProfile.profile.militaryServiceStatus.eq(militaryServiceStatus));
        if(isNotBlank(username)){
            booleanExpressionList.add(QProfile.profile.username.like("%" + username + "%"));
        }
        if(isNotBlank(nationalId)){
            booleanExpressionList.add(QProfile.profile.nationalId.like("%" + nationalId + "%"));
        }
        if(isNotBlank(foreignerId)){
            booleanExpressionList.add(QProfile.profile.foreignerId.like("%" + foreignerId + "%"));
        }
        if(isNotBlank(email)){
            booleanExpressionList.add(QProfile.profile.email.like("%" + email + "%"));
        }
        if(isNotBlank(address)){
            booleanExpressionList.add(QProfile.profile.address.like("%" + address + "%"));
        }
        if(isNotBlank(firstName)){
            booleanExpressionList.add(QProfile.profile.firstName.like("%" + firstName + "%"));
        }
        if(isNotBlank(lastName)){
            booleanExpressionList.add(QProfile.profile.lastName.like("%" + lastName + "%"));
        }
        if(isNotBlank(mobileNumber)){
            booleanExpressionList.add(QProfile.profile.mobileNumber.like("%" + mobileNumber + "%"));
        }
        if(isNotBlank(phoneNumber)){
            booleanExpressionList.add(QProfile.profile.mobileNumber.like("%" + mobileNumber + "%"));
        }
        return booleanExpressionList;
    }
}