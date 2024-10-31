package org.taba.notification.model.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SMSResponseMessageDto {
    private int status;
    private long id;
    private int userId;
    private int parts;
    private double tariff;
    private String alphabet;
    private String recipient;
}
