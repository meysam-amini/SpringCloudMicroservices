package com.meysam.common.model.entity;


import com.meysam.common.model.entity.BaseEntity;
import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "userwallet",columnNames = {"UserId","coinUnit"})})
public class UserWallet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigDecimal Id;

    @Column(nullable = false)
    private BigDecimal userId;

    @Column(nullable = false)
    private String coinUnit;

    @Column(nullable = false)
    private String address;

}
