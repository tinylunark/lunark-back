package com.lunark.lunark.service;


import com.lunark.lunark.model.*;
import com.lunark.lunark.repository.IPropertyRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Service
public class PropertyService implements IPropertyService {
    @Autowired
    IPropertyRepository propertyRepository;

    @Override
    public Collection<Property> findAll() {
        return propertyRepository.findAll();
    }

    @Override
    public Optional<Property> find(Long id) {
        return propertyRepository.findById(id);
    }

    @Override
    public Property create(Property property) {
        return propertyRepository.saveAndFlush(property);
    }

    @Override
    public Property update(Property property) {
        if(propertyRepository.findById(property.getId()).isEmpty()) {
            return null;
        }
        return propertyRepository.saveAndFlush(property);
    }

    @Override
    public void delete(Long id) {
        propertyRepository.deleteById(id);
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
