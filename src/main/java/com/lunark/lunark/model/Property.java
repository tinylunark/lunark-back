package com.lunark.lunark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @OneToOne
    @NotNull
    private Address address;
    //TODO: Store images
    @Transient
    private Collection<Image> photos;
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
}