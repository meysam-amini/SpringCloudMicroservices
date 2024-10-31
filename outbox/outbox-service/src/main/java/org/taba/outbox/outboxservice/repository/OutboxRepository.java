package org.taba.outbox.outboxservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.taba.common.dao.repository.BaseRepository;
import org.taba.outbox.outboxservice.model.entity.OutBox;
import org.taba.outbox.outboxservice.model.enums.OutboxEventStatus;

import java.util.List;

@NoRepositoryBean
public interface OutboxRepository<T extends OutBox> extends BaseRepository<T> {

    @Query("select T from #{#entityName} T  where T.status = :status and T.createdDate < :timeInMillis")
    Page<T> findAllByStatus(OutboxEventStatus status, long timeInMillis, Pageable pageable);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Modifying
    @Query("update #{#entityName} T set T.status = :status , T.retryCount=T.retryCount+1" +
            " where T.status in :validStatuses and T.id = :id")
    int updateStatusInDistinctTransaction(Long id, OutboxEventStatus status, List<OutboxEventStatus> validStatuses);

}
