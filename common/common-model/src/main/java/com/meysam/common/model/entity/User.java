package com.meysam.common.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigDecimal Id;

    @Column(nullable = false , unique = true)
    private String username;

    @NotNull(message = "First name cannot be null")
    @Size(min = 2,message = "First name cannot be less than 2 chars")
    @Column(nullable = false , length = 50)
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 2,message = "Last name cannot be less than 2 chars")
    @Column(nullable = false , length = 50)
    private String lastName;

//    @NotNull(message = "Password cannot be null")
//    @Size(min = 8,max = 16,message = "16> Password >8")
//    private String password;
//
//    @Column(nullable = false , unique = true)
//    private String encryptedPassword;


    @Email
    @Column(nullable = false , length = 50 , unique = true)
    private String email;

    @Column(nullable = false , length = 11 , unique = true)
    private String phone;


}
