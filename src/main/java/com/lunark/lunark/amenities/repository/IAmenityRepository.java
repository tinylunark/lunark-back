package com.lunark.lunark.amenities.repository;

import com.lunark.lunark.amenities.model.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAmenityRepository extends JpaRepository<Amenity, Long> {
    List<Amenity> findAll();
}
