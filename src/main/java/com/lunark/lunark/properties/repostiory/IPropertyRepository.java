package com.lunark.lunark.properties.repostiory;

import com.lunark.lunark.properties.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface IPropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {
    Optional<Property> findById(Long id);
}
