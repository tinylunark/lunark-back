package com.lunark.lunark.service;


import com.lunark.lunark.model.*;
import com.lunark.lunark.repository.IPropertyImageRepository;
import com.lunark.lunark.repository.IPropertyRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

@Service
public class PropertyService implements IPropertyService {
    private final IPropertyRepository propertyRepository;
    private final IPropertyImageRepository propertyImageRepository;

    @Autowired
    public PropertyService(IPropertyRepository propertyRepository, IPropertyImageRepository propertyImageRepository) {
        this.propertyRepository = propertyRepository;
        this.propertyImageRepository = propertyImageRepository;
    }

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
        try {
            propertyRepository.save(property);
            propertyRepository.flush();
            return property;
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
            StringBuilder sb = new StringBuilder(1000);
            for (ConstraintViolation<?> error : errors) {
                sb.append(error.getMessage() + "\n");
            }
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                    sb.toString());
        }
    }

    @Override
    public Property update(Property property) {
        try {
            find(property.getId());
            propertyRepository.save(property);
            propertyRepository.flush();
            return property;
        } catch (RuntimeException ex) {
            Throwable e = ex; Throwable c = null;
            while((e != null) && !((c = e.getCause()) instanceof ConstraintViolationException)){
                e = (RuntimeException) c;
            }
            if ((c != null) && (c instanceof ConstraintViolationException)) {
                ConstraintViolationException c2 = (ConstraintViolationException) c;
                Set<ConstraintViolation<?>> errors = c2.getConstraintViolations();
                StringBuilder sb = new StringBuilder(1000);
                for (ConstraintViolation<?> error : errors) {
                    sb.append(error.getMessage() + "\n");
                }
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, sb.toString());
            }
            throw ex;
        }
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

    @Override
    public void saveImage(Property property, MultipartFile file) throws IOException {
        byte[] byteObjects = new byte[file.getBytes().length];

        int i = 0;

        for (byte b : file.getBytes()){
            byteObjects[i++] = b;
        }

        PropertyImage propertyImage = new PropertyImage();
        propertyImage.setImageData(byteObjects);
        propertyImage.setProperty(property);
        propertyImage.setMimeType(file.getContentType());

        property.getImages().add(propertyImage);

        propertyRepository.save(property);
        propertyImageRepository.save(propertyImage);
    }

    @Override
    @Transactional
    public Optional<PropertyImage> getImage(Long imageId, Long propertyId) {
        return propertyImageRepository.findByIdAndProperty(imageId, propertyId);
    }
}
