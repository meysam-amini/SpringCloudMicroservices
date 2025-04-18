package com.meysam.common.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {


    @Version
    @Column(nullable = false)
    private Long version= 0L;

    @Column(nullable = false)
    private boolean enabled=true;

    @CreationTimestamp
    private Date createdDate = new Date();

    @UpdateTimestamp
    private Date updatedDate = new Date();

}
