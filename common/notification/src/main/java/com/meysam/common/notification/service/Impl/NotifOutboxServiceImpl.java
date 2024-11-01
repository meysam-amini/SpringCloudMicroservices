package com.meysam.common.notification.service.Impl;

import com.meysam.common.notification.model.dto.FailedSMSDto;
import com.meysam.common.notification.model.entity.FailedNotif;
import com.meysam.common.notification.repository.FailedNotifRepository;
import com.meysam.common.notification.service.api.NotifOutboxService;
import com.meysam.common.notification.service.api.NotificationService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.taba.outbox.outboxservice.model.enums.OutboxEventStatus;
import org.taba.outbox.outboxservice.service.Impl.OutboxServiceImpl;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;


@Slf4j
@Service
public class NotifOutboxServiceImpl extends OutboxServiceImpl<FailedNotif> implements NotifOutboxService {

    private final NotificationService notificationService;
    private final FailedNotifRepository failedNotifRepository;
    public NotifOutboxServiceImpl(NotificationService notificationService, FailedNotifRepository failedNotifRepository) {
        super(failedNotifRepository);
        this.failedNotifRepository=failedNotifRepository;
        this.notificationService = notificationService;
    }

    @PostConstruct
    public void init() {
        notificationService.setNotifOutboxService(this);
    }

    @Override
    public FailedNotif save(FailedSMSDto failedSMSDto, Integer outboxTrackingCode) {
        try {
            FailedNotif failedNotif;
            if (Objects.isNull(outboxTrackingCode)) {
                outboxTrackingCode = (UUID.randomUUID() + "" + System.currentTimeMillis()).hashCode();
                failedNotif = FailedNotif.builder()
                        .sender(failedSMSDto.getSender())
                        .msg(failedSMSDto.getMessage())
                        .destination(failedSMSDto.getRecipient())
                        .type(failedSMSDto.getType())
                        .build();
                failedNotif.setOutboxTrackingCode(outboxTrackingCode);
                failedNotif.setRetryCount(0);
            }else {
                failedNotif = failedNotifRepository.findByOutboxTrackingCode(outboxTrackingCode).orElse(null);
                if(Objects.isNull(failedNotif)) {
                    log.error("On saving new failed notification with outbox tracking code:{}, we couldn't find related record at time :{}",outboxTrackingCode,System.currentTimeMillis());
                    return null;
                }
            }
            failedNotif.setStatus(OutboxEventStatus.UNSENT);
            failedNotif.setCreatedDate(new Date());
            return failedNotifRepository.save(failedNotif);

        }catch(Exception e){
                log.error("exception on saving failed notification to outbox at time:{} , data was: {}, exception is:{}", System.currentTimeMillis(), failedSMSDto.toString(), e);
                return null;
            }
    }

    @Override
    public void process() {
        super.process();
    }

    @Override
    public void retry(FailedNotif failedNotif) {
        notificationService.sendNotification(failedNotif.getMsg(), failedNotif.getType(), failedNotif.getDestination(), failedNotif.getOutboxTrackingCode());
    }

    public int changeStatusToSent(int outboxTrackingCode) {
        try {
            FailedNotif failedNotif = failedNotifRepository.findByOutboxTrackingCode(outboxTrackingCode).orElse(null);
            if (Objects.nonNull(failedNotif)) {
                return failedNotifRepository.updateStatusInDistinctTransaction(failedNotif.getId(), OutboxEventStatus.SENT, OutboxEventStatus.getAllValidStatusesForSent());
            }
            return 0;
        }catch (Exception e){
            log.error("On changing status of failed notification with outbox tracking code:{}, exception occurred at time:{}, exception is:{}",outboxTrackingCode,System.currentTimeMillis(),e);
            return 0;
        }
    }
}
