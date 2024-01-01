package com.meysam.notification.notificationservice.service.Impl;


import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.notification.notificationservice.model.dto.FailedSMSDto;
import com.meysam.notification.notificationservice.model.entity.FailedNotif;
import com.meysam.notification.notificationservice.repository.FailedNotifRepository;
import com.meysam.notification.notificationservice.service.api.FailedNotifOutBoxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FailedNotifOutBoxServiceImpl implements FailedNotifOutBoxService {

    private final FailedNotifRepository failedNotifRepository;
    private final LocaleMessageSourceService messageSourceService;

    public FailedNotifOutBoxServiceImpl(FailedNotifRepository failedNotifRepository, LocaleMessageSourceService messageSourceService) {
        this.failedNotifRepository = failedNotifRepository;
        this.messageSourceService = messageSourceService;
    }

    @Override
    public FailedNotif save(FailedSMSDto failedSMSDto) {
        try {
            FailedNotif failedNotif = FailedNotif.builder()
                    .sender(failedSMSDto.getSender())
                    .msg(failedSMSDto.getMessage())
                    .destination(failedSMSDto.getRecipient())
                    .type(failedSMSDto.getType())
                    .build();
            return failedNotifRepository.save(failedNotif);
        }catch (Exception e){
            log.error("exception on saving outbox failed sms at time:{} , data was: {}, exception is:{}",System.currentTimeMillis(),failedSMSDto.toString(),e);
            return null;
        }
    }
}
