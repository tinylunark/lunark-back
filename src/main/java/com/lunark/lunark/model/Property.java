package com.lunark.lunark.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import java.awt.Image;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public boolean setAvailabilityEntries(Collection<PropertyAvailabilityEntry> newAvailabilityEntries) {
        if (this.willCloseOnReservedDate(newAvailabilityEntries) || this.willPriceChangeOnReservedDate(newAvailabilityEntries)) {
            return false;
        }

        for (PropertyAvailabilityEntry newEntry: newAvailabilityEntries) {
            Optional<PropertyAvailabilityEntry> oldEntry = this.getAvailabilityEntry(newEntry.getDate());
            if(oldEntry.isPresent()) {
                newEntry.setReserved(oldEntry.get().isReserved());
            }
        }

        this.availabilityEntries = newAvailabilityEntries;
        return true;
    }

    private boolean willCloseOnReservedDate(Collection<PropertyAvailabilityEntry> newAvailabilityEntries) {
        Set<LocalDate> reservedDates = this.availabilityEntries.stream()
                .filter(propertyAvailabilityEntry -> propertyAvailabilityEntry.isReserved())
                .map(propertyAvailabilityEntry -> propertyAvailabilityEntry.getDate())
                .collect(Collectors.toSet());
        Set<LocalDate> newAvailableDates = newAvailabilityEntries.stream()
                .map(propertyAvailabilityEntry -> propertyAvailabilityEntry.getDate())
                .collect(Collectors.toSet());

        return !newAvailableDates.containsAll(reservedDates);
    }

    private boolean willPriceChangeOnReservedDate(Collection<PropertyAvailabilityEntry> newAvailabilityEntries) {
        for (PropertyAvailabilityEntry newEntry: newAvailabilityEntries) {
            Optional<PropertyAvailabilityEntry> oldEntry = this.getAvailabilityEntry(newEntry.getDate());
            if(oldEntry.isPresent() && oldEntry.get().isReserved() && oldEntry.get().getPrice() != newEntry.getPrice()) {
                return true;
            }
        }

        return false;
    }

    public enum PropertyType {
        DUPLEX,
        APARTMENT,
        COTTAGE,
        HUT
    }

    public boolean isAvailable(LocalDate day) {
        for (PropertyAvailabilityEntry availabilityEntry : availabilityEntries) {
            if (availabilityEntry.isFor(day) && !availabilityEntry.isReserved()) {
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

    private Optional<PropertyAvailabilityEntry> getAvailabilityEntry(LocalDate day) {
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