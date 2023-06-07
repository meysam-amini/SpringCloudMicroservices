package com.meysam.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.Date;

@Getter
@Setter
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
