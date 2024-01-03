package com.lunark.lunark.notifications.model;

public enum NotificationType {
    PROPERTY_REVIEW("PROPERTY_REVIEW"),
    HOST_REVIEW("HOST_REVIEW"),
    RESERVATION_CREATED("RESERVATION_CREATED"),
    RESERVATION_CANCELED("RESERVATION_CANCELED"),
    RESERVATION_ACCEPTED("RESERVATION_ACCEPTED"),
    RESERVATION_REJECTED("RESERVATION_REJECTED");
    private String name;
    NotificationType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
