package com.lunark.lunark.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidMinMaxGuestsValidator.class)
public @interface ValidMinMaxGuests {
    String message() default "Minimum number of guests must be less than or equal to the maximum number of guests";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
