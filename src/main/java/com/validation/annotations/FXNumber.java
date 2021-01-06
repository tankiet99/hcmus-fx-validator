package com.validation.annotations;

import com.validation.FXAbstractValidator;
import com.validation.NumberValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FXNumber {
    Class<? extends FXAbstractValidator> validation() default NumberValidator.class;
    double min() default 0;
    double max() default 10000;
    String message() default "This field must not be null!";
}
