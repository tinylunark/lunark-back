package com.lunark.lunark.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class ValidPropertySearchPricesValidator implements ConstraintValidator<ValidPropertySearchPrices, Object[]> {
    @Override
    public void initialize(ValidPropertySearchPrices constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object[] objects, ConstraintValidatorContext constraintValidatorContext) {
        Double minPrice = (Double) objects[6];
        Double maxPrice = (Double) objects[7];
        if (minPrice == null || maxPrice == null) {
            return true;
        }
        return minPrice < maxPrice;
    }
}
