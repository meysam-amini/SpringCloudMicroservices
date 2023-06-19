package com.meysam.common.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class GeneralProperties extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank
    @Column(unique = true)
    private String settingKey;
    private String settingValue;
}
