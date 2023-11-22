package com.lunark.lunark.dto;

import java.time.LocalDate;

public class ReservationDto {
    private Long id;
    private String propertyName;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private Long guestId;
    private int numberOfGuests;
    private String status;
    private int cancellationCount;

    public ReservationDto(Long id, String propertyName, LocalDate startDate, LocalDate endDate, double price, Long guestId, int numberOfGuests, String status, int cancellationCount) {
        this.id = id;
        this.propertyName = propertyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.guestId = guestId;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
        this.cancellationCount = cancellationCount;
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

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCancellationCount() {
        return cancellationCount;
    }

    public void setCancellationCount(int cancellationCount) {
        this.cancellationCount = cancellationCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
