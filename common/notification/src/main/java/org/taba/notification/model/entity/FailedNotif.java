package org.taba.notification.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.taba.notification.model.constants.NotificationConstants;
import org.taba.outbox.outboxservice.model.entity.OutBox;

@Table(name = "FAILEDNOTIF")
@Entity
@Getter
@Setter@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FailedNotif extends OutBox {

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
    private NotificationConstants.NotificationType type;


}
