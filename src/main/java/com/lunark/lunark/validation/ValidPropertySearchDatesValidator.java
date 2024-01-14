package com.lunark.lunark.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;

import java.time.LocalDate;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class ValidPropertySearchDatesValidator implements ConstraintValidator<ValidPropertySearchDates, Object[]> {
    @Override
    public void initialize(ValidPropertySearchDates constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object[] objects, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate startDate = (LocalDate) objects[2];
        LocalDate endDate = (LocalDate) objects[3];
        return startDate.isBefore(endDate);
    }
}
