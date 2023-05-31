package com.meysam.model;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "memberwallet",columnNames = {"member_id","coin_unit"})})
public class MemberWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigDecimal Id;

    @Column(nullable = false)
    private BigDecimal memberId;

    @Column(nullable = false)
    private String coinUnit;

    @Column(nullable = false)
    private String address;

}
