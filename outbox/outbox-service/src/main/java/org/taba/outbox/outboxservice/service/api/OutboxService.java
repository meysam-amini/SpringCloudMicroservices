package org.taba.outbox.outboxservice.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.taba.outbox.outboxservice.model.entity.OutBox;
import org.taba.outbox.outboxservice.model.enums.OutboxEventStatus;

import java.util.List;

public interface OutboxService<T extends OutBox> {

    void process();

    Page<T> findAllFailed(Pageable pageable);

    int updateStatusInDistinctTransaction (Long id, OutboxEventStatus status, List<OutboxEventStatus> validStatuses);

    void retry(T t);
}
