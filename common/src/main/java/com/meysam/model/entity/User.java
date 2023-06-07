package com.meysam.model.entity;

import com.meysam.model.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigDecimal Id;

    @Column(nullable = false , unique = true)
    private String userId;

    @NotNull(message = "First name cannot be null")
    @Size(min = 2,message = "First name cannot be less than 2 chars")
    @Column(nullable = false , length = 50)
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 2,message = "Last name cannot be less than 2 chars")
    @Column(nullable = false , length = 50)
    private String lastName;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8,max = 16,message = "16> Password >8")
    private String password;

    @Column(nullable = false , unique = true)
    private String encryptedPassword;


    @Email
    @Column(nullable = false , length = 120 , unique = true)
    private String email;


}
