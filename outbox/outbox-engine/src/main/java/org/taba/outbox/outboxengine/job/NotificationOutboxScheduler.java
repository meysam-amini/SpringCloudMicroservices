package org.taba.outbox.outboxengine.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.taba.notification.service.api.NotifOutboxService;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationOutboxScheduler {

    private final NotifOutboxService notifOutboxService;


//    @Scheduled(cron = "0 0 0 * * *",zone = "Asia/Tehran")
    @Scheduled(fixedDelay = 60*1000*60)
    public void scheduler(){

        log.info("start scheduling failed notifications job at time:{}",System.currentTimeMillis());
        long startTime= System.currentTimeMillis();
        try {
            notifOutboxService.process();

        }catch (Exception e){
            log.error("Exception at processing failed notification job at time:{}, exception is:{} ",System.currentTimeMillis(),e);
        }
        long endTime= System.currentTimeMillis();
        log.info("end of scheduling failed notifications job at time:{}, whole job took:{} minutes",System.currentTimeMillis(),(endTime-startTime)/60000);

    }
}
