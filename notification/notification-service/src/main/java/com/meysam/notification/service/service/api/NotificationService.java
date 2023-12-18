package com.meysam.notification.service.service.api;


import com.meysam.notification.service.model.constants.NotificationConstants;

public interface NotificationService {

    boolean sendNotification(String msg, NotificationConstants.NotificationType notificationType, String destination);
}
