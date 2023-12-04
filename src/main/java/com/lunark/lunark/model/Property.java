package com.lunark.lunark.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int minGuests;
    private int maxGuests;
    private String description;
    private double latitude;
    private double longitude;
    @OneToOne
    private Address address;
    //TODO: Store images
    @Transient
    private Collection<Image> photos;
    private boolean approved;
    private PricingMode pricingMode;
    private int cancellationDeadline;
    private boolean autoApproveEnabled;
    @OneToMany
    private Collection<Review> reviews = new ArrayList<>();
    @OneToMany
    private Collection<PropertyAvailabilityEntry> availabilityEntries = new ArrayList<>();
    @OneToMany
    private Collection<Amenity> amenities = Arrays.asList(new Amenity(1L, "Wi-Fi", null), new Amenity(2L, "Washing machine", null));

    public enum PropertyType {
        DUPLEX,
        APARTMENT,
        COTTAGE,
        HUT
    }
}