package com.meysam.common.notification.service.api;


import com.meysam.common.notification.model.dto.FailedSMSDto;
import com.meysam.common.notification.model.entity.FailedNotif;
import org.taba.outbox.outboxservice.service.api.OutboxService;

public interface NotifOutboxService extends OutboxService<FailedNotif> {

    int changeStatusToSent(int trackingCode);
    FailedNotif save(FailedSMSDto failedSMSDto, Integer trackingCode);

}
