package com.meysam.common.outbox.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.taba.common.model.model.entity.notification.OutBox;
import org.taba.common.model.model.enums.OutboxEventStatus;

import java.util.List;

public interface OutboxService<T extends OutBox> {

    void process();

    Page<T> findAllFailed(Pageable pageable);

    int updateStatusInDistinctTransaction (Long id, OutboxEventStatus status, List<OutboxEventStatus> validStatuses);

    void retry(T t);
}
