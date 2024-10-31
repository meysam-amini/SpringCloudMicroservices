package org.taba.notification.model.dto;

import lombok.*;
import org.taba.notification.model.constants.NotificationConstants;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FailedSMSDto {

    private String sender;
    private String message;
    private String recipient;
    private NotificationConstants.NotificationType type;
    private Integer trackingCode;

}
