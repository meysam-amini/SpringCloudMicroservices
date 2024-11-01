package com.meysam.common.notification.service.Impl;


import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import com.meysam.common.notification.model.constants.NotificationConstants;
import com.meysam.common.notification.model.dto.FailedSMSDto;
import com.meysam.common.notification.model.dto.SendSMSRequestDto;
import com.meysam.common.notification.model.dto.SendSMSResponseDto;
import com.meysam.common.notification.service.api.NotifOutboxService;
import com.meysam.common.notification.service.api.NotificationService;

import java.util.Objects;


@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    @Value("${notification.sms.enabled:#{true}}")
    private String SMS_ENABLED;

    @Value("${notification.sms.provider.uri}")
    private String SMS_PROVIDER_URI;

    @Value("${notification.sms.sender-number}")
    private String SMS_SENDER_NUMBER;

//    @Value("${notification.sms.provider.authorization-header}")
//    private String AUTHORIZATION_HEADER;

    @Value("${notification.email.provider.uri}")
    private String EMAIL_PROVIDER_URI;

    @Value("${notification.email.enabled:#{false}}")
    private String EMAIL_ENABLED;



    private final LocaleMessageSourceService messageSourceService;
    private final WebClient webClient;
    private NotifOutboxService notifOutboxService;


    public NotificationServiceImpl(LocaleMessageSourceService messageSourceService, WebClient webClient) {
        this.messageSourceService = messageSourceService;
        this.webClient = webClient;
    }

    public void setNotifOutboxService(NotifOutboxService notifOutboxService){
        this.notifOutboxService = notifOutboxService;
    }


    private boolean sendNotification(String msg, NotificationConstants.NotificationType notificationType, String destination, Integer outboxTrackingCode,boolean retryable) {

        switch (notificationType){

            case SMS -> {
                if(Boolean.parseBoolean(SMS_ENABLED)) {
                    return sendSMS(msg,destination, outboxTrackingCode,retryable);
                }else {
                    throw new BusinessException(messageSourceService.getMessage("SMS_NOTIFICATION_SERVICE_IS_DISABLED"));
                }
            }

            case EMAIL -> {
                if(Boolean.parseBoolean(EMAIL_ENABLED)) {
                    return sendEmail(msg,destination, outboxTrackingCode,retryable);
                }else {
                    throw new BusinessException(messageSourceService.getMessage("EMAIL_NOTIFICATION_SERVICE_IS_DISABLED"));
                }
            }
        }

        return false;
    }

    @Override
    public boolean sendNotification(String msg, NotificationConstants.NotificationType notificationType, String destination){
        return sendNotification(msg,notificationType,destination,null,true);
    }

    @Override
    public boolean sendNotification(String msg, NotificationConstants.NotificationType notificationType, String destination, Integer outboxTrackingCode) {
        return sendNotification(msg,notificationType,destination,outboxTrackingCode,true);
    }

    @Override
    public boolean sendNotification(String msg, NotificationConstants.NotificationType notificationType, String destination, boolean isRetryable) {
        return sendNotification(msg,notificationType,destination,null,isRetryable);
    }


    private boolean sendSMS(String msg,String phoneNumber,Integer outboxTrackingCode,Boolean retryable){
        restCallToSMSProvider(msg,phoneNumber,outboxTrackingCode,retryable);
        return true;
    }

    private boolean sendEmail(String msg,String email,Integer outboxTrackingCode,Boolean retryable){

        return false;
    }


    private void restCallToSMSProvider(String msg,String destinationPhoneNumber,Integer outboxTrackingCode,Boolean retryable){


        SendSMSRequestDto requestDto = SendSMSRequestDto.getSendSMSRequestDto(SMS_SENDER_NUMBER,msg,destinationPhoneNumber);
        Boolean failed=false;
        try {
            SendSMSResponseDto response = webClient
                    .post()
                    .uri(SMS_PROVIDER_URI)
                    .headers(
                            httpHeaders -> {
                                httpHeaders.set("Authorization","Basic dGFuemltXzAwMDk1L21jbHNfaXRlOkM2b1ppQUdQSlFkQ0Q1TlU=");
                                httpHeaders.set("Content-Type", "application/json");
                            }).body(BodyInserters.fromValue(requestDto))
                    .retrieve().bodyToMono(SendSMSResponseDto.class).block();
            if(response.getStatus()==0) {
                log.info("SMS with msg: " + msg + " sent to number: " + destinationPhoneNumber + " at time: " + System.currentTimeMillis() + " response was: " + response);
                if(Objects.nonNull(outboxTrackingCode)) {
                    changeStatusToSentInOutbox(outboxTrackingCode);
                }
            }
            else {
                failed=true;
            }
        }catch (Exception e){
            log.error("Exception on calling sms provider at time:{}, exception is:{}",System.currentTimeMillis(),e);
//            throw new BusinessException(messageSourceService.getMessage("PROBLEM_ON_SENDING_SMS"));
            failed=true;
        }
        if((retryable) && (failed)) {
            saveFailedSmsToOutBox(requestDto, outboxTrackingCode);
        }
    }

    private void changeStatusToSentInOutbox(Integer outboxTrackingCode) {
        notifOutboxService.changeStatusToSent(outboxTrackingCode);
    }

    private void saveFailedSmsToOutBox(SendSMSRequestDto requestDto,Integer outboxTrackingCode) {
            FailedSMSDto failedSMSDto = FailedSMSDto.builder()
                    .message(requestDto.getMessages().get(0))
                    .sender(requestDto.getSenders().get(0))
                    .recipient(requestDto.getRecipients().get(0))
                    .type(NotificationConstants.NotificationType.SMS)
                    .build();
            notifOutboxService.save(failedSMSDto,outboxTrackingCode);
        }

}
