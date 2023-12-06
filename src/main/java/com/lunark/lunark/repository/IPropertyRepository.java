package com.lunark.lunark.repository;

import com.lunark.lunark.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPropertyRepository extends JpaRepository<Property, Long> {
}
