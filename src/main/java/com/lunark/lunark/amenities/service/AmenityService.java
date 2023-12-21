package com.lunark.lunark.amenities.service;

import com.lunark.lunark.amenities.model.Amenity;
import com.lunark.lunark.amenities.repository.IAmenityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AmenityService implements IAmenityService {
    private IAmenityRepository amenityRepository;

    @Autowired
    public AmenityService(IAmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    @Override
    public List<Amenity> findAll() {
        return amenityRepository.findAll();
    }

    @Override
    public Amenity create(Amenity amenity) {
        return amenityRepository.save(amenity);
    }

    @Override
    public Optional<Amenity> findById(Long id) {
        return amenityRepository.findById(id);
    }

    @Override
    public Amenity update(Amenity amenity) {
        Optional<Amenity> savedAmenity = findById(amenity.getId());

        return savedAmenity.map(a -> {
            a.setName(amenity.getName());
            return amenityRepository.save(a);
        }).orElseGet(() -> amenityRepository.save(amenity));
    }

    @Override
    public void delete(Long id) {
        amenityRepository.deleteById(id);
    }
}
