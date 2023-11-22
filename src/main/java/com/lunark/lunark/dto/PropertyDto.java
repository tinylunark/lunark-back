package com.lunark.lunark.dto;

import com.lunark.lunark.model.Address;

public class PropertyDto {
    private String name;
    private int minGuests;
    private int maxGuests;
    private String description;
    private double latitude;
    private double longitude;
    private Address address;

    public PropertyDto() {}

    public PropertyDto(String name, int minGuests, int maxGuests, String description, double latitude, double longitude, Address address) {
        this.name = name;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinGuests() {
        return minGuests;
    }

    public void setMinGuests(int minGuests) {
        this.minGuests = minGuests;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
