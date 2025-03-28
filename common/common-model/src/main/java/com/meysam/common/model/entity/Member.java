package com.meysam.common.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    @SequenceGenerator(name = "member_seq", sequenceName = "member_seq", allocationSize = 1)
    private long id;

    @Column(nullable = false , unique = true)
    private String username;

    @NotNull(message = "First name cannot be null")
    @Size(min = 2,message = "First name cannot be less than 2 chars")
    @Column(nullable = true , length = 50)
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 2,message = "Last name cannot be less than 2 chars")
    @Column(nullable = true , length = 50)
    private String lastName;

    @Email
    @Column(nullable = false , length = 50 , unique = true)
    private String email;

    @Column(nullable = true , length = 11 , unique = true)
    private String phone;


}
