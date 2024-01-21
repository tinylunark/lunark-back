package com.lunark.lunark.validation;

import com.lunark.lunark.amenities.service.IAmenityService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AmenitiesExistsConstraintValidator implements ConstraintValidator<AmenitiesExistConstraint, List<Long>> {
    @Autowired
    IAmenityService amenityService;

    @Override
    public void initialize(AmenitiesExistConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<Long> ids, ConstraintValidatorContext constraintValidatorContext) {
        return ids.stream().allMatch(id -> amenityService.findById(id).isPresent());
    }
}
