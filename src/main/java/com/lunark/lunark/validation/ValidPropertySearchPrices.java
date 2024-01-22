package com.lunark.lunark.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPropertySearchPricesValidator.class)
public @interface ValidPropertySearchPrices {
    String message() default "Minimum price must be less than maximum price";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
