package com.meysam.common.notification.repository;

import com.meysam.common.notification.model.entity.FailedNotif;
import org.springframework.stereotype.Repository;
import org.taba.outbox.outboxservice.repository.OutboxRepository;

import java.util.Optional;

@Repository
public interface FailedNotifRepository extends OutboxRepository<FailedNotif> {

    Optional<FailedNotif> findByOutboxTrackingCode(int trackingCode);

}
