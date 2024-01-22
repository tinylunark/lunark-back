package com.lunark.lunark.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=AccountExistsConstraintValidator.class)
public @interface AccountExistsConstraint {
    String message() default "No account with the specified id exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
