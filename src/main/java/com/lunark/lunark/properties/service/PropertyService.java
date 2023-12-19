package com.lunark.lunark.properties.service;


import com.lunark.lunark.properties.dto.PropertySearchDto;
import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.properties.model.PropertyAvailabilityEntry;
import com.lunark.lunark.properties.model.PropertyImage;
import com.lunark.lunark.properties.repostiory.IPropertyImageRepository;
import com.lunark.lunark.properties.repostiory.IPropertyRepository;
import com.lunark.lunark.properties.specification.PropertySpecification;
import com.lunark.lunark.reviews.model.Review;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.Clock;
import java.util.*;

import static java.util.Locale.filter;

@Service
public class PropertyService implements IPropertyService {
    // TODO: add logic
    private IPropertyRepository propertyRepository;
    private final IPropertyImageRepository propertyImageRepository;
    private Clock clock;

    @Autowired
    public PropertyService(IPropertyRepository propertyRepository, IPropertyImageRepository propertyImageRepository, Clock clock) {
        this.propertyRepository = propertyRepository;
        this.propertyImageRepository = propertyImageRepository;
        this.clock = clock;
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
            for(PropertyAvailabilityEntry entry: property.getAvailabilityEntries()) {
                entry.setProperty(property);
            }
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
    public Property update(Property newProperty, Long id) {
        try {
            Optional<Property> property = find(id);
            if (property.isEmpty()) {
                return this.create(newProperty);
            } else {
                property.get().setName(newProperty.getName());
                property.get().setAddress(newProperty.getAddress());
                property.get().getAmenities().clear();
                property.get().getAmenities().addAll(newProperty.getAmenities());
                property.get().setDescription(newProperty.getDescription());
                property.get().setMinGuests(newProperty.getMinGuests());
                property.get().setMaxGuests(newProperty.getMaxGuests());
                property.get().setLongitude(newProperty.getLongitude());
                property.get().setLatitude(newProperty.getLatitude());
                property.get().setAvailabilityEntries(newProperty.getAvailabilityEntries());
                property.get().setAutoApproveEnabled(newProperty.isAutoApproveEnabled());
                property.get().setType(newProperty.getType());
                property.get().setPricingMode(newProperty.getPricingMode());
                property.get().setCancellationDeadline(newProperty.getCancellationDeadline());
                property.get().setAmenities(newProperty.getAmenities());
                property.get().setHost(newProperty.getHost());

                propertyRepository.save(property.get());
                propertyRepository.flush();
                return property.get();
            }
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
    public boolean changePricesAndAvailability(Long id, Collection<PropertyAvailabilityEntry> newPricesAndAvailability) {
        Optional<Property> propertyOptional = this.find(id);
        if (propertyOptional.isEmpty()){
            return false;
        }
        Property property = propertyOptional.get();
        property.setClock(clock);
        for(PropertyAvailabilityEntry entry: newPricesAndAvailability) {
            entry.setProperty(property);
        }
        if (!property.setAvailabilityEntries(newPricesAndAvailability)) {
            return false;
        }
        update(property, property.getId());
        return true;

    }

    @Override
    public List<Property> findByFilter(PropertySearchDto filter) {
        Specification<Property> specification = new PropertySpecification(filter);
        return propertyRepository.findAll(specification);
    }

    @Override
    public List<Property> findAllPropertiesForHost(Long hostId) {
        return propertyRepository.findAll().stream().filter(property -> Objects.equals(property.getHost().getId(), hostId)).toList();
    }


    @Override
    public List<Property> findUnapproved() {
        return propertyRepository.findAll().stream().filter(property -> !property.isApproved()).toList();
    }

    @Override
    public void delete(Long id) {
        propertyRepository.deleteById(id);
        propertyRepository.flush();
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
