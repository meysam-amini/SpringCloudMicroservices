package com.meysam.common.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table@Entity
public class Coin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coin_seq")
    @SequenceGenerator(name = "coin_seq", sequenceName = "coin_seq", allocationSize = 1)
    private long id;

    @NotNull(message = "coin symbol cannot be null")
    @Column(nullable = false)
    private String symbol;

    @NotNull(message = "coin network cannot be null")
    @Column(nullable = false)
    private String network;

}
