package com.meysam.common.model.entity;


import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "memberwallet",columnNames = {"member_id","coin_unit"})})
public class MemberWallet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigDecimal Id;

    @JoinColumn(name = "member_id")
    @ManyToOne(cascade =  CascadeType.ALL,fetch = FetchType.EAGER)
    private Member member;

    @Column(name = "coin_unit",nullable = false)
    private String coinUnit;

    @Column(nullable = false)
    private String address;

}
