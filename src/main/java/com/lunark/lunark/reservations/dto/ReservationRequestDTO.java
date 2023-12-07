package com.lunark.lunark.reservations.dto;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

public class ReservationRequestDTO {
    private Long propertyId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long guestId;
    private int numberOfGuests;

    public ReservationRequestDTO(Long propertyId, LocalDate startDate, LocalDate endDate, Long guestId, int numberOfGuests) {
        this.propertyId = propertyId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guestId = guestId;
        this.numberOfGuests = numberOfGuests;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
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
}
