package com.meysam.common.model.entity;

import com.meysam.common.model.entity.BaseEntity;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table@Entity
public class Coin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigDecimal Id;

    @NotNull(message = "coin symbol cannot be null")
    @Column(nullable = false)
    private String symbol;

    @NotNull(message = "coin network cannot be null")
    @Column(nullable = false)
    private String network;

}
