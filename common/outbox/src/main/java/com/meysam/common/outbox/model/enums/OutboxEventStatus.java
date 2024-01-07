package com.meysam.common.outbox.model.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum OutboxEventStatus {
    SENT,SENDING,UNSENT,RETRY_LIMIT_EXCEEDED;

    public static List<OutboxEventStatus> getAllValidStatusesForSending(){
        return new ArrayList<>(Arrays.asList(UNSENT));
    }

    public static List<OutboxEventStatus> getAllValidStatusesForSent(){
        return new ArrayList<>(Arrays.asList(SENDING,UNSENT));
    }

    public static List<OutboxEventStatus> getAllValidStatusesForRetryLimitExceed(){
        return new ArrayList<>(Arrays.asList(SENDING,UNSENT));
    }

    public static List<OutboxEventStatus> getAllValidStatusesForUnsent(){
        return new ArrayList<>(Arrays.asList(SENDING));
    }
}
