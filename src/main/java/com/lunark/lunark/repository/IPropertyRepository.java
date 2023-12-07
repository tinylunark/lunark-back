package com.lunark.lunark.repository;

import com.lunark.lunark.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPropertyRepository extends JpaRepository<Property, Long> {
    Optional<Property> findById(Long id);
}
