package org.taba.outbox.outboxservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.taba.outbox.outboxservice.model.enums.OutboxEventStatus;

import java.util.Date;

@Getter
@MappedSuperclass
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OutBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "STATUS")
    private OutboxEventStatus status= OutboxEventStatus.UNSENT;

    @Column(name = "CREATEDDATE")
    @CreationTimestamp
    private Date createdDate = new Date();

    @Column(name = "TRACKINGCODE")
    private Integer outboxTrackingCode;

    @Column(name = "RETRYCOUNT")
    private Integer retryCount;
}
