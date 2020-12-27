package com.validation.annotations;

import com.validation.FXAbstractValidator;
import com.validation.RequiredValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FXString {
    Class<? extends FXAbstractValidator> validation() default RequiredValidator.class;
    int max() default 100;
    int min() default 0;
    String message() default "This field must not be null!";
}