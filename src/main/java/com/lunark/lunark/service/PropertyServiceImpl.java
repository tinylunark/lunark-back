package com.lunark.lunark.service;

import com.lunark.lunark.model.Property;
import com.lunark.lunark.model.Review;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {
    // TODO: add logic

    @Override
    public Collection<Property> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Optional<Property> find(Long id) {
        return Optional.of(new Property());
    }

    @Override
    public Property create(Property property) {
        return new Property();
    }

    @Override
    public Property update(Property property) {
        return new Property();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Double getAverageGrade(Long id) {
        Optional<Property> property = this.find(id);
        if (property.isEmpty()){
            return -1.0;
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
}
