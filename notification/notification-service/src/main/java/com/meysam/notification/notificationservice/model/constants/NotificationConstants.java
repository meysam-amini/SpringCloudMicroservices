package com.meysam.notification.notificationservice.model.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class NotificationConstants {

    public enum NotificationType {
        EMAIL,
        SMS
    }

    public enum NotificationDestinationGroup {
        COMPLAINING,
        COMPLAINANT,
        REPRESENTATIVE
    }

    @Getter
    @AllArgsConstructor
    public enum NotificationMessages {

        _01("msg sent to:{}"),
        ;
        private String text;

    }

}
