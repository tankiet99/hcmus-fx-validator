package com.validation.annotations;

import com.validation.FXAbstractValidator;
import com.validation.validator.IntegerValidator;
import com.validation.validator.NumberValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FXInteger {
    public Class<? extends FXAbstractValidator> validation() default IntegerValidator.class;
    int min() default Integer.MIN_VALUE;
    int max() default Integer.MAX_VALUE;
    String message() default "Number is not valid integer!";
}
