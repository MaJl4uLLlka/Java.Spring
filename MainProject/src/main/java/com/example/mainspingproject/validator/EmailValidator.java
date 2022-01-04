package com.example.mainspingproject.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailValid, String> {

    @Override
    public void initialize(EmailValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if(email == null){
            return false;
        }

        if(email.matches("^[a-zA-Z][0-9a-zA-Z]{3,31}@(mail|gmail).com")){
            return true;
        }
        else{
            return false;
        }
    }
}
