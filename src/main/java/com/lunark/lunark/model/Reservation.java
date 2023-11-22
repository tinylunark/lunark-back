package com.lunark.lunark.model;

import com.fasterxml.jackson.databind.annotation.JsonAppend;

import java.time.LocalDate;

public class Reservation {
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfGuests;
    private ReservationStatus status;
    private double price;
    private Property property;
    private Account guest;
}
