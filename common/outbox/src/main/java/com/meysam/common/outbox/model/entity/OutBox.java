package com.meysam.common.outbox.model.entity;

import com.meysam.common.outbox.model.enums.OutboxEventStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@MappedSuperclass
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OutBox {

    

    @Column(name = "STATUS")
    private OutboxEventStatus status= OutboxEventStatus.UNSENT;

    @Column(name = "CREATEDDATE")
    @CreationTimestamp
    private Date createdDate = new Date();

    @Column(name = "TRACKINGCODE")
    private Integer trackingCode;

    @Column(name = "RETRYCOUNT")
    private Integer retryCount;
}
