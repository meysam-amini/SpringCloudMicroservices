package com.meysam.common.outbox.repository;

import com.meysam.common.dao.BaseRepository;
import com.meysam.common.outbox.model.entity.OutBox;
import com.meysam.common.outbox.model.enums.OutboxEventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
