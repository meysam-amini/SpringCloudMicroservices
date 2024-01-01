package com.meysam.notification.service.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Table(name = "FAILEDNOTIF")
@Entity
@Getter
@Setter@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FailedNotif {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "invalid sender")
    @Size(max = 50)
    @Column(name = "SENDER")
    private String sender;

    @NotNull(message = "invalid msg")
    @Column(name = "MSG")
    private String msg;

    @NotNull(message = "invalid destination")
    @Column(name = "DESTINATION")
    private String destination;

    @NotNull(message = "invalid type")
    @Column(name = "TYPE")
    private String type;

    @Column(name = "ISSENT")
    private boolean isSent=false;
}
