package com.meysam.model.entity;

import com.meysam.walletmanager.model.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity@Table
@Getter@Setter@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Resource extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigDecimal Id;
}
