package com.meysam.common.model.entity;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneralProperties extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigDecimal Id;

    @NotBlank
    @Column(unique = true)
    private String key;
    private String value;
}
