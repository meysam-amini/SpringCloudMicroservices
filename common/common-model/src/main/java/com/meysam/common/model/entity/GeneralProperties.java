package com.meysam.common.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@Entity

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class GeneralProperties extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "general_properties_seq")
    @SequenceGenerator(name = "general_properties_seq", sequenceName = "general_properties_seq", allocationSize = 1)
    private long id;

    @NotBlank
    @Column(unique = true)
    private String settingKey;
    private String settingValue;
}
