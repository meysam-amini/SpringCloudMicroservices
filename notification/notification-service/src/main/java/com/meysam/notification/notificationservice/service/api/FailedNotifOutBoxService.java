package com.meysam.notification.notificationservice.service.api;


import com.meysam.notification.notificationservice.model.dto.FailedSMSDto;
import com.meysam.notification.notificationservice.model.entity.FailedNotif;

public interface FailedNotifOutBoxService {

    FailedNotif save(FailedSMSDto failedSMSDto);
}
