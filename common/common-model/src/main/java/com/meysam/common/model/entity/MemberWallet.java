package com.meysam.common.model.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@Getter
@Setter@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "memberwallet",columnNames = {"member_id","coin_unit"})})
public class MemberWallet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memberwallet_seq")
    @SequenceGenerator(name = "memberwallet_seq", sequenceName = "memberwallet_seq", allocationSize = 1)
    private long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(cascade =  CascadeType.ALL,fetch = FetchType.EAGER)
    private Member member;

    @Column(name = "coin_unit",nullable = false)
    private String coinUnit;

    @Column(nullable = false)
    private String address;

}
