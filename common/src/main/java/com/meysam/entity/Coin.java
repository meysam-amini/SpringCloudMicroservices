package com.meysam.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Coin {

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
