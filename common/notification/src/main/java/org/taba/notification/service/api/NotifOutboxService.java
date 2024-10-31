package org.taba.notification.service.api;


import org.taba.notification.model.dto.FailedSMSDto;
import org.taba.notification.model.entity.FailedNotif;
import org.taba.outbox.outboxservice.service.api.OutboxService;

public interface NotifOutboxService extends OutboxService<FailedNotif> {

    int changeStatusToSent(int trackingCode);
    FailedNotif save(FailedSMSDto failedSMSDto, Integer trackingCode);

}
