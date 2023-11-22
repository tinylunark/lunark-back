package com.lunark.lunark.model;

public enum ReservationStatus {
    PENDING("pending"),
    ACCEPTED("accepted"),
    REJECTED("rejected");

    private String name;


    ReservationStatus(String name) {
        this.name = name;
    }

    public static ReservationStatus fromString(String value) {
        switch (value) {
            case "pending":
                return ReservationStatus.PENDING;
            case "accepted":
                return ReservationStatus.ACCEPTED;
            case "rejected":
                return ReservationStatus.REJECTED;
        }
        throw new IllegalArgumentException("Invalid string in account role");
    }

    @Override
    public String toString() {
        return this.name;
    }
}
