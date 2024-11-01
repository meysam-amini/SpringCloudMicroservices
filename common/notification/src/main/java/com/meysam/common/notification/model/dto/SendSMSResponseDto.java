package com.meysam.common.notification.model.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter@Builder
public class SendSMSResponseDto {
    /*

    sample data from magfa sms provider :{"status":0,"messages":[{"status":0,"id":134512228059,"userId":0,"parts":1,"tariff":1320.0,"alphabet":"DEFAULT","recipient":"989353837909"}]}

     */
    private int status;
    private List<SMSResponseMessageDto> messages;

}
