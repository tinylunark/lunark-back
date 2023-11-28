package com.lunark.lunark.model;

import java.time.LocalDate;

public class PropertyAvailabilityEntry {
    private LocalDate date;
    private double price;

    private boolean isReserved = false;

    public PropertyAvailabilityEntry() {
    }

    public PropertyAvailabilityEntry(LocalDate date, double price) {
        this.date = date;
        this.price = price;
        this.isReserved = false;
    }

    public PropertyAvailabilityEntry(LocalDate date, double price, boolean isReserved) {
        this.date = date;
        this.price = price;
        this.isReserved = isReserved;
    }

    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isFor(LocalDate date) {
        return this.date.equals(date);
    }

    public boolean isReserved() {
        return this.isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
