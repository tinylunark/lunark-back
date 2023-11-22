package com.lunark.lunark.service;

import com.lunark.lunark.model.Property;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {
    // TODO: add logic

    @Override
    public Collection<Property> findAll() {
        return new ArrayList<>();
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
}
