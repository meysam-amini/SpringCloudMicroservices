package com.meysam.auth.model.entity;

import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long Id;
}
