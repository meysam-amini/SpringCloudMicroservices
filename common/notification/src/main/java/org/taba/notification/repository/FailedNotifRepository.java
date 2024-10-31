package org.taba.notification.repository;

import org.springframework.stereotype.Repository;
import org.taba.notification.model.entity.FailedNotif;
import org.taba.outbox.outboxservice.repository.OutboxRepository;

import java.util.Optional;

@Repository
public interface FailedNotifRepository extends OutboxRepository<FailedNotif> {

    Optional<FailedNotif> findByOutboxTrackingCode(int trackingCode);

}
