package com.lunark.lunark.notifications.model;

public enum NotificationType {
    PROPERTY_REVIEW("PROPERTY_REVIEW"),
    HOST_REVIEW("HOST_REVIEW"),
    RESERVATION_CREATED("RESERVATION_CREATED"),
    RESERVATION_CANCELLED("RESERVATION_CANCELLED"),
    RESERVATION_REPLY("RESERVATION_REPLY");
    private String name;
    NotificationType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
