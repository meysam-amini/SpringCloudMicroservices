package com.meysam.common.notification.service.api;


import com.meysam.common.notification.model.constants.NotificationConstants;

public interface NotificationService {
    void setNotifOutboxService(NotifOutboxService notifOutboxService);
    //for sending normal notifications from any business:
    boolean sendNotification(String msg, NotificationConstants.NotificationType notificationType, String destination);
    //for send from failed notifications retry process(which has a tracking code):
    boolean sendNotification(String msg, NotificationConstants.NotificationType notificationType, String destination,Integer trackingCode);
    //for covering sending otp(which doesn't need outbox & retry process):
    boolean sendNotification(String msg, NotificationConstants.NotificationType notificationType, String destination,boolean isRetryable);
}
