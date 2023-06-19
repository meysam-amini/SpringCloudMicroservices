package com.meysam.common.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneralProperties extends BaseEntity {

    @Id
    @GeneratedValue
    private Long Id;

    @NotBlank
    @Column(unique = true)
    private String key;
    private String value;
}
