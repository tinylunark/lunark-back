package com.lunark.lunark.reservations.dto;

import java.time.LocalDate;

public class ReservationConfirmationDto {
    private String propertyName;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private int numberOfGuests;

    public ReservationConfirmationDto() {

    }

    public ReservationConfirmationDto(String propertyName, LocalDate startDate, LocalDate endDate, double price, int numberOfGuests) {
        this.propertyName = propertyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.numberOfGuests = numberOfGuests;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
}
