package org.taba.notification.model.dto;

import lombok.*;

import java.util.Collections;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SendSMSRequestDto {

    private List<String> senders;
    private List<String> messages;
    private List<String> recipients;

    public static SendSMSRequestDto getSendSMSRequestDto(String sender,String msg,String recipient){
        return SendSMSRequestDto.builder()
                .senders(Collections.singletonList(sender))
                .messages(Collections.singletonList(msg))
                .recipients(Collections.singletonList(recipient))
                .build();
    }

}
