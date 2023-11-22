package com.lunark.lunark.model;

import java.awt.Image;
import java.util.Collection;
import java.util.Objects;

public class Property {
    private Long id;
    private String name;
    private int minGuests;
    private int maxGuests;
    private String description;
    private double latitude;
    private double longitude;
    private Address address;
    private Collection<Image> photos;
    private boolean approved;
    private PricingMode pricingMode;
    private int cancellationDeadline;
    private boolean autoApproveEnabled;

    public Property() {
    }

    public Property(Long id, String name, int minGuests, int maxGuests, String description, double latitude, double longitude, Address address, Collection<Image> photos, boolean approved, PricingMode pricingMode, int cancellationDeadline, boolean autoApproveEnabled) {
        this.id = id;
        this.name = name;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.photos = photos;
        this.approved = approved;
        this.pricingMode = pricingMode;
        this.cancellationDeadline = cancellationDeadline;
        this.autoApproveEnabled = autoApproveEnabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Collection<Image> getPhotos() {
        return photos;
    }

    public void setPhotos(Collection<Image> photos) {
        this.photos = photos;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public PricingMode getPricingMode() {
        return pricingMode;
    }

    public void setPricingMode(PricingMode pricingMode) {
        this.pricingMode = pricingMode;
    }

    public int getCancellationDeadline() {
        return cancellationDeadline;
    }

    public void setCancellationDeadline(int cancellationDeadline) {
        this.cancellationDeadline = cancellationDeadline;
    }

    public boolean isAutoApproveEnabled() {
        return autoApproveEnabled;
    }

    public void setAutoApproveEnabled(boolean autoApproveEnabled) {
        this.autoApproveEnabled = autoApproveEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return minGuests == property.minGuests && maxGuests == property.maxGuests && Double.compare(latitude, property.latitude) == 0 && Double.compare(longitude, property.longitude) == 0 && approved == property.approved && cancellationDeadline == property.cancellationDeadline && autoApproveEnabled == property.autoApproveEnabled && Objects.equals(name, property.name) && Objects.equals(description, property.description) && Objects.equals(address, property.address) && Objects.equals(photos, property.photos) && pricingMode == property.pricingMode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, minGuests, maxGuests, description, latitude, longitude, address, photos, approved, pricingMode, cancellationDeadline, autoApproveEnabled);
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", minGuests=" + minGuests +
                ", maxGuests=" + maxGuests +
                ", description='" + description + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", address=" + address +
                ", photos=" + photos +
                ", approved=" + approved +
                ", pricingMode=" + pricingMode +
                ", cancellationDeadline=" + cancellationDeadline +
                ", autoApproveEnabled=" + autoApproveEnabled +
                '}';
    }
}