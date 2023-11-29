package com.lunark.lunark.service;


import com.lunark.lunark.model.*;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {
    // TODO: add logic

    @Override
    public Collection<Property> findAll() {
        return Arrays.asList(
                new Property(1L,
                        "Vila Golija",
                        1,
                        5,
                        "Vila pored Semeteskog jezera",
                        45,
                        45,
                        new Address("Negde", "Semetes", "Serbia"),
                        new ArrayList<Image>(),
                        true,
                        PricingMode.WHOLE_UNIT,
                        24,
                        true,
                        new ArrayList<Review>(),
                        new ArrayList<PropertyAvailabilityEntry>(),
                        Arrays.asList(new Amenity(1L, "Wi-Fi", null))
                ),
                new Property(2L,
                        "Hotel Oderberger",
                        1,
                        5,
                        "Located in Berlin, 1.1 miles from Memorial of the Berlin Wall, Hotel Oderberger has accommodations with a garden, private parking, a terrace and a bar. Among the facilities at this property are a concierge service and a tour desk, along with free WiFi throughout the property. Natural History Museum, Berlin is 1.6 miles away and Alexanderplatz is 1.6 miles from the hotel.\n",
                        45,
                        45,
                        new Address("Oderberger Straße 57", "Berlin", "Germany"),
                        new ArrayList<Image>(),
                        true,
                        PricingMode.WHOLE_UNIT,
                        24,
                        true,
                        new ArrayList<Review>(),
                        new ArrayList<PropertyAvailabilityEntry>(),
                        Arrays.asList(new Amenity(1L, "Wi-Fi", null), new Amenity(3L, "Indoor swimming pool", null))
                )
        );
    }

    @Override
    public Optional<Property> find(Long id) {
        return Optional.of(new Property());
    }

    @Override
    public Property create(Property property) {
        return new Property();
    }

    @Override
    public Property update(Property property) {
        return new Property();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Double getAverageGrade(Long id) {
        Optional<Property> property = this.find(id);
        if (property.isEmpty()){
            return null;
        }
        Property foundProperty = property.get();
        return calculateAverageGrade(foundProperty);
    }

    private Double calculateAverageGrade(Property property) {
        ArrayList<Review> reviewList = (ArrayList<Review>) property.getReviews();
        if (reviewList.isEmpty()) {
            return 0.0;
        }
        double sum = reviewList.stream().mapToDouble(Review::getRating).sum();
        return sum / reviewList.size();
    }
}
