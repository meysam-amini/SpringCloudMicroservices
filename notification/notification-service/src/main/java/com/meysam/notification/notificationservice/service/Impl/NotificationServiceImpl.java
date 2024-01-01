package com.meysam.notification.notificationservice.service.Impl;


import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.notification.notificationservice.model.constants.NotificationConstants;
import com.meysam.notification.notificationservice.model.dto.FailedSMSDto;
import com.meysam.notification.notificationservice.model.dto.SendSMSRequestDto;
import com.meysam.notification.notificationservice.model.dto.SendSMSResponseDto;
import com.meysam.notification.notificationservice.service.api.FailedNotifOutBoxService;
import com.meysam.notification.notificationservice.service.api.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    @Value("${notification.sms.enabled:#{true}}")
    private String SMS_ENABLED;

    @Value("${notification.sms.provider.uri}")
    private String SMS_PROVIDER_URI;

    @Value("${notification.sms.sender-number}")
    private String SMS_SENDER_NUMBER;

    @Value("${notification.sms.provider.authorization-header}")
    private String AUTHORIZATION_HEADER;

    @Value("${notification.email.provider.uri}")
    private String EMAIL_PROVIDER_URI;

    @Value("${notification.email.enabled:#{false}}")
    private String EMAIL_ENABLED;



    private final LocaleMessageSourceService messageSourceService;
    private final WebClient webClient;
    private final FailedNotifOutBoxService failedNotifOutBoxService;


    public NotificationServiceImpl(LocaleMessageSourceService messageSourceService, WebClient webClient, FailedNotifOutBoxService failedNotifOutBoxService) {
        this.messageSourceService = messageSourceService;
        this.webClient = webClient;
        this.failedNotifOutBoxService = failedNotifOutBoxService;
    }


    @Override
    public boolean sendNotification(String msg, NotificationConstants.NotificationType notificationType, String destination) {

        switch (notificationType){

            case SMS -> {
                if(Boolean.parseBoolean(SMS_ENABLED)) {
                    return sendSMS(msg,destination);
                }else {
                    throw new BusinessException(messageSourceService.getMessage("SMS_NOTIFICATION_SERVICE_IS_DISABLED"));
                }
            }

            case EMAIL -> {
                if(Boolean.parseBoolean(EMAIL_ENABLED)) {
                    return sendEmail(msg,destination);
                }else {
                    throw new BusinessException(messageSourceService.getMessage("EMAIL_NOTIFICATION_SERVICE_IS_DISABLED"));
                }
            }
        }

        return false;
    }



    private boolean sendSMS(String msg,String phoneNumber){
        restCallToSMSProvider(msg,phoneNumber);
        return true;
    }

    private boolean sendEmail(String msg,String email){

        return false;
    }


    private void restCallToSMSProvider(String msg,String destinationPhoneNumber){


        SendSMSRequestDto requestDto = SendSMSRequestDto.getSendSMSRequestDto(SMS_SENDER_NUMBER,msg,destinationPhoneNumber);
        try {
            SendSMSResponseDto smsResponseDto = webClient
                    .post()
                    .uri(SMS_PROVIDER_URI)
                    .headers(
                            httpHeaders -> {
                                httpHeaders.set("Authorization",AUTHORIZATION_HEADER);
                                httpHeaders.set("Content-Type", "application/json");
                            }).body(BodyInserters.fromValue(requestDto))
                    .retrieve().bodyToMono(SendSMSResponseDto.class).block();
            if(smsResponseDto.getStatus()==0)
                log.info("SMS with msg: "+msg+" sent to number: "+destinationPhoneNumber+" at time: "+System.currentTimeMillis()+ " response was: "+smsResponseDto.toString());
            else
                saveFailedSmsToOutBox(requestDto);
        }catch (Exception e){
            log.error("Exception on calling sms provider at time:{}, exception is:{}",System.currentTimeMillis(),e);
//            throw new BusinessException(messageSourceService.getMessage("PROBLEM_ON_SENDING_SMS"));
            saveFailedSmsToOutBox(requestDto);
        }

    }

    private void saveFailedSmsToOutBox(SendSMSRequestDto requestDto) {
        //should set the retry limit for sending sms
        FailedSMSDto failedSMSDto = FailedSMSDto.builder()
                .message(requestDto.getMessages().get(0))
                .sender(requestDto.getSenders().get(0))
                .recipient(requestDto.getRecipients().get(0))
                .type(NotificationConstants.NotificationType.SMS.name())
                .build();
        failedNotifOutBoxService.save(failedSMSDto);
    }
}
