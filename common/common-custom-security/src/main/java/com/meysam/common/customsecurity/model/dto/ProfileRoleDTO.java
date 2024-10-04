package com.meysam.common.customsecurity.model.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRoleDTO implements Serializable {

    Long id;

    @NotNull(message = "invalid role")
    Long role;

    @NotNull(message = "invalid profile")
    Long profile;

    Boolean isActive;

    @NotNull(message = "invalid date time")
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

    @NotNull
    @NumberFormat
    Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    Date modifiedAt;

    Long modifiedBy;

}