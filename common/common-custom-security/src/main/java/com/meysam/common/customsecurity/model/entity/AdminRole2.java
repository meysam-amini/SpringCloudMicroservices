//package com.meysam.common.customsecurity.model.entity;
//
//import com.meysam.common.model.entity.BaseEntity;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.Data;
//
//import java.io.Serializable;
//import java.math.BigInteger;
//
//@Table(name = "ADMINROLE")
//@Data
//@Entity
//public class AdminRole extends BaseEntity implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//
//
//    @NotNull(message = "invalid role")
//    @Column(name = "ROLE")
//    Long role;
//
//    @NotNull(message = "invalid profile")
//    @Column(name = "admin")
//    Long admin;
//}