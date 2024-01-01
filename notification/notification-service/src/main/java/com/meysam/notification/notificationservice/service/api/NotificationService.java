package com.meysam.notification.notificationservice.service.api;


import com.meysam.notification.notificationservice.model.constants.NotificationConstants;

public interface NotificationService {

    boolean sendNotification(String msg, NotificationConstants.NotificationType notificationType, String destination);
}
