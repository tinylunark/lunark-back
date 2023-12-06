package com.lunark.lunark.repository;

import com.lunark.lunark.model.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IPropertyImageRepository extends JpaRepository<PropertyImage, Long> {
    @Query("SELECT e FROM PropertyImage e WHERE e.id = :imageId AND e.property.id = :propertyId")
    Optional<PropertyImage> findByIdAndProperty(Long imageId, Long propertyId);
}
