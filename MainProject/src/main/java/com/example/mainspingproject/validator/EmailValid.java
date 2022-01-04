package com.example.mainspingproject.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface EmailValid {
    String message() default "Invalid email";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
