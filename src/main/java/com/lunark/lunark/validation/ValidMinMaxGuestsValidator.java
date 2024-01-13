package com.lunark.lunark.validation;

import com.lunark.lunark.properties.dto.PropertyRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidMinMaxGuestsValidator implements ConstraintValidator<ValidMinMaxGuests, PropertyRequestDto> {
    @Override
    public void initialize(ValidMinMaxGuests constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PropertyRequestDto propertyRequestDto, ConstraintValidatorContext constraintValidatorContext) {
        return propertyRequestDto.getMinGuests() < propertyRequestDto.getMaxGuests();
    }
}
