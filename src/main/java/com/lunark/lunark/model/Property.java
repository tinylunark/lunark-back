package com.lunark.lunark.model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
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
    private Collection<Review> reviews = new ArrayList<>();
    private Collection<PropertyAvailabilityEntry> availabilityEntries = new ArrayList<>();
    private Collection<Amenity> amenities = Arrays.asList(new Amenity(1L, "Wi-Fi", null), new Amenity(2L, "Washing machine", null));



    public Property() {
    }

    public Property(Long id, String name, int minGuests, int maxGuests, String description, double latitude, double longitude, Address address, Collection<Image> photos, boolean approved, PricingMode pricingMode, int cancellationDeadline, boolean autoApproveEnabled, Collection<Review> reviews, Collection<PropertyAvailabilityEntry> availabilityEntries, Collection<Amenity> amenities) {
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
        this.reviews = reviews;
        this.availabilityEntries = availabilityEntries;
        this.amenities = amenities;
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


    public Collection<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<Review> reviews) {
        this.reviews = reviews;
    }

    public Collection<PropertyAvailabilityEntry> getAvailabilityEntries() {
        return availabilityEntries;
    }

    public void setAvailabilityEntries(Collection<PropertyAvailabilityEntry> availabilityEntries) {
        this.availabilityEntries = availabilityEntries;
    }

    public Collection<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(Collection<Amenity> amenities) {
        this.amenities = amenities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return getMinGuests() == property.getMinGuests() && getMaxGuests() == property.getMaxGuests() && Double.compare(getLatitude(), property.getLatitude()) == 0 && Double.compare(getLongitude(), property.getLongitude()) == 0 && isApproved() == property.isApproved() && getCancellationDeadline() == property.getCancellationDeadline() && isAutoApproveEnabled() == property.isAutoApproveEnabled() && Objects.equals(getId(), property.getId()) && Objects.equals(getName(), property.getName()) && Objects.equals(getDescription(), property.getDescription()) && Objects.equals(getAddress(), property.getAddress()) && Objects.equals(getPhotos(), property.getPhotos()) && getPricingMode() == property.getPricingMode() && Objects.equals(getReviews(), property.getReviews());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getMinGuests(), getMaxGuests(), getDescription(), getLatitude(), getLongitude(), getAddress(), getPhotos(), isApproved(), getPricingMode(), getCancellationDeadline(), isAutoApproveEnabled(), getReviews());
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", name='" + name + '\'' +
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
                ", reviews=" + reviews +
                ", availabilityEntries=" + availabilityEntries +
                '}';
    }
}