package com.meysam.auth.model.entity;

import com.meysam.common.entity.BaseEntity;
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
