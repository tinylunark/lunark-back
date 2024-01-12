package com.lunark.lunark.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})    // definise nad cime anotacija moze da se koristi
@Retention(RetentionPolicy.RUNTIME)     // definise politiku zadrzavanja anotacije
@Constraint(validatedBy=AccountExistsConstraintValidator.class)       // povezuje anotaciju sa validatorom
public @interface AccountExistsConstraint {
    String message() default "No account with the specified id exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
