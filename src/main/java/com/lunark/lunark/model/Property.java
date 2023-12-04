package com.lunark.lunark.model;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import org.springframework.cglib.core.Local;

import java.awt.Image;
import java.time.LocalDate;
import java.util.*;

@Entity
@Data
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @Positive
    private int minGuests;
    @Positive
    private int maxGuests;
    @NotBlank
    private String description;
    private double latitude;
    private double longitude;
    @NotNull
    @Valid
    private Address address;
    //TODO: Store images
    @OneToMany(
            mappedBy = "property",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Collection<PropertyImage> images;
    private boolean approved = false;
    private PricingMode pricingMode = PricingMode.PER_PERSON;
    private int cancellationDeadline;
    private boolean autoApproveEnabled = false;
    @OneToMany
    private Collection<Review> reviews = new ArrayList<>();
    @OneToMany
    private Collection<PropertyAvailabilityEntry> availabilityEntries = new ArrayList<>();
    @OneToMany
    private Collection<Amenity> amenities = new ArrayList<>();

    public enum PropertyType {
        DUPLEX,
        APARTMENT,
        COTTAGE,
        HUT
    }

    public boolean isAvailable(LocalDate day) {
        for (PropertyAvailabilityEntry availabilityEntry : availabilityEntries) {
            if (availabilityEntry.isFor(day)) {
                return true;
            }
        }
        return false;
    }
    public boolean isAvailable(LocalDate from, LocalDate to) {
        for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1L)) {
            if (!isAvailable(date)) {
                return false;
            }
        }

        return true;
    }

    public Optional<Reservation> reserve(LocalDate from, LocalDate to, int numberOfGuests) {
        if (!isAvailable(from, to)) {
            return Optional.ofNullable(null);
        }

        return Optional.of(new Reservation(null, from, to, numberOfGuests, autoApproveEnabled ? ReservationStatus.ACCEPTED : ReservationStatus.PENDING, 1000, this, null));
    }

    public Optional<PropertyAvailabilityEntry> getAvailabilityEntry(LocalDate day) {
        for (PropertyAvailabilityEntry availabilityEntry : availabilityEntries) {
            if (availabilityEntry.isFor(day)) {
                return Optional.of(availabilityEntry);
            }
        }
        return Optional.ofNullable(null);
    }

    public boolean makeAvailable(LocalDate day, double price) {
        if (isAvailable(day)) {
            return false;
        }

        PropertyAvailabilityEntry availabilityEntry = new PropertyAvailabilityEntry(day, price, this);
        this.availabilityEntries.add(availabilityEntry);

        return true;
    }

    public boolean makeAvailable(Collection<PropertyAvailabilityEntry> availabilityEntries) {
        for (PropertyAvailabilityEntry availabilityEntry: availabilityEntries) {
            if (isAvailable(availabilityEntry.getDate())) {
                return false;
            }
        }

        this.availabilityEntries.addAll(availabilityEntries);
        return true;
    }
}