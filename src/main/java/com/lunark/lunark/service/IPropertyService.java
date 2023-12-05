package com.lunark.lunark.service;

import com.lunark.lunark.model.Property;
import com.lunark.lunark.model.PropertyImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public interface IPropertyService {
    Collection<Property> findAll();
    Optional<Property> find(Long id);
    Property create(Property property);
    Property update(Property property);
    void delete(Long id);
    Double getAverageGrade(Long id);
    void saveImage(Property property, MultipartFile file) throws IOException;
    Optional<PropertyImage> getImage(Long imageId, Long propertyId);
}
