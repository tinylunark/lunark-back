package com.lunark.lunark.dto;

import java.time.LocalDate;

public class ReservationDto {
    private String propertyName;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private Long guestId;

    public ReservationDto(String propertyName, LocalDate startDate, LocalDate endDate, double price, Long guestId) {
        this.propertyName = propertyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.guestId = guestId;
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

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }
}
