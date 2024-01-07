package com.meysam.common.outbox.service.api;

import com.meysam.common.outbox.model.entity.OutBox;
import com.meysam.common.outbox.model.enums.OutboxEventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OutboxService<T extends OutBox> {

    void process();

    Page<T> findAllFailed(Pageable pageable);

    int updateStatusInDistinctTransaction (Long id, OutboxEventStatus status, List<OutboxEventStatus> validStatuses);

    void retry(T t);
}
