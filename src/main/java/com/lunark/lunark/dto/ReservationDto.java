package com.lunark.lunark.dto;

import com.lunark.lunark.model.Reservation;

import java.time.LocalDate;

public class ReservationDto {
    private Long id;
    private Long propertyId;
    private String propertyName;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private Long guestId;
    private int numberOfGuests;
    private String status;
    private int cancellationCount;

    public ReservationDto(Long id, Long propertyId, String propertyName, LocalDate startDate, LocalDate endDate, double price, Long guestId, int numberOfGuests, String status, int cancellationCount) {
        this.id = id;
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.guestId = guestId;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
        this.cancellationCount = cancellationCount;
    }

    public ReservationDto(Reservation reservation) {
        this.id = reservation.getId();
        this.propertyId = reservation.getProperty().getId();
        this.propertyName = reservation.getProperty().getName();
        this.startDate = reservation.getStartDate();
        this.endDate = reservation.getEndDate();
        this.price = reservation.getPrice();
        this.guestId = reservation.getGuest().getId();
        this.numberOfGuests = reservation.getNumberOfGuests();
        this.status = reservation.getStatus().toString();
        this.cancellationCount = 0;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
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
