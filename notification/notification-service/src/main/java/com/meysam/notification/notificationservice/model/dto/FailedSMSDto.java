package com.meysam.notification.notificationservice.model.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FailedSMSDto {

    private String sender;
    private String message;
    private String recipient;
    private String type;

}
