package com.lunark.lunark.service;

import com.lunark.lunark.model.Property;

import java.util.Collection;
import java.util.Optional;

public interface PropertyService {
    Collection<Property> findAll();
    Optional<Property> find(Long id);
    Property create(Property property);
    Property update(Property property);
    void delete(Long id);
}
