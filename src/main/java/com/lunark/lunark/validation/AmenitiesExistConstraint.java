package com.lunark.lunark.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AmenitiesExistsConstraintValidator.class)
public @interface AmenitiesExistConstraint {
    String message() default "Can not find all amenities in the list by id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
