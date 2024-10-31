package com.meysam.common.notification.model.dto;

import lombok.*;
import com.meysam.common.notification.model.constants.NotificationConstants;

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
