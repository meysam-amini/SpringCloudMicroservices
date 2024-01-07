package com.meysam.common.outbox.service.Impl;

import com.meysam.common.outbox.model.entity.OutBox;
import com.meysam.common.outbox.model.enums.OutboxEventStatus;
import com.meysam.common.outbox.repository.OutboxRepository;
import com.meysam.common.outbox.service.api.OutboxService;
import com.meysam.common.utils.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class OutboxServiceImpl<T extends OutBox> implements OutboxService<T> {

    private final OutboxRepository outboxRepository;

    @Value("${outbox.processing.page.size:#{100}}")
    private Integer processingPageSize;

    @Value("${outbox.retry.count:#{3}}")
    private Integer retryCount;

    @Override
    public void process() {
        Pageable firstPagePageable = PaginationUtils.getFirstPagePageable(processingPageSize);
        Page<T> allFailedPageable = findAllFailed(firstPagePageable);
        processPageByPage(allFailedPageable);
    }

    private void processPageByPage(Page<T> allFailedPageable) {
        List<T> allFailed = allFailedPageable.getContent();
        if (allFailed.isEmpty())
            return;

        String logUniqueIdentifier = UUID.randomUUID().toString();

        log.info("start to retry failed events with identifier : {} ,data: {}",logUniqueIdentifier, allFailed.stream().map(OutBox::getId).collect(Collectors.toList()));
        List<T> validOutbox = new ArrayList<>();
        for (T item : allFailed) {
            if(item.getRetryCount()>=retryCount){
                int effectedRow = updateStatusInDistinctTransaction(item.getId(), OutboxEventStatus.RETRY_LIMIT_EXCEEDED, OutboxEventStatus.getAllValidStatusesForRetryLimitExceed());
            }else {
                int effectedRow = updateStatusInDistinctTransaction(item.getId(), OutboxEventStatus.SENDING, OutboxEventStatus.getAllValidStatusesForSending());
                if (effectedRow != 0) {
                    validOutbox.add(item);
                }
            }
        }
        validOutbox.forEach(this::retry);
        log.info("complete retrying failed events with identifier : {} data:{}", logUniqueIdentifier, allFailed.stream().map(OutBox::getId).collect(Collectors.toList()));

        Pageable firstPagePageable = PaginationUtils.getFirstPagePageable(processingPageSize);
        Page<T> outboxEvents = findAllFailed(firstPagePageable);
        processPageByPage(outboxEvents);
    }


    @Override
    public Page<T> findAllFailed(Pageable pageable) {
        Calendar calendar= Calendar.getInstance();
        calendar.add(Calendar.MINUTE,-5);
        return outboxRepository.<T>findAllByStatus(OutboxEventStatus.UNSENT,calendar.getTimeInMillis(),pageable);
    }

    @Override
    public int updateStatusInDistinctTransaction(Long id, OutboxEventStatus status, List<OutboxEventStatus> validStatuses) {
        return outboxRepository.<T>updateStatusInDistinctTransaction(id,status,validStatuses);
    }

    @Override
    public abstract void retry(T t);


}
