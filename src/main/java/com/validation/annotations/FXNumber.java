package com.validation.annotations;

import com.validation.FXAbstractValidator;
import com.validation.validator.NumberValidator;

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
    double min() default Double.MIN_VALUE;
    double max() default Double.MAX_VALUE;
    String message() default "Number is not valid!";
}
