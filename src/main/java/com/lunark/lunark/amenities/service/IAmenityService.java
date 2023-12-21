package com.lunark.lunark.amenities.service;


import com.lunark.lunark.amenities.model.Amenity;

import java.util.List;
import java.util.Optional;

public interface IAmenityService {
    List<Amenity> findAll();
    Amenity create(Amenity amenity);
    Optional<Amenity> findById(Long id);
    Amenity update(Amenity amenity);
    void delete(Long id);
}
