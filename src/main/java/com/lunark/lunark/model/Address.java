package com.lunark.lunark.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Address{
    String street;
    String city;
    String country;
    @Id
    @OneToOne
    Property property;

    public Address() {

    }

    public Address(String street, String city, String country) {
        this.street = street;
        this.city = city;
        this.country = country;
    }
}
