package com.meysam.common.entity;

import com.meysam.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @Column(nullable = false)
    private int wrongEnteredOtpCount=3;
    @Column(nullable = false)
    private boolean isEmailActive=true;
    @Column(nullable = false)
    private boolean isSMSActive=true;
    @Column(nullable = false)
    private int otpDurationInSeconds=60;
}
