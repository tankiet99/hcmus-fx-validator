package com.validation.annotations;

import com.validation.FXAbstractValidator;
import com.validation.validator.RegexValidator;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FXRegex {
    public Class<? extends FXAbstractValidator> validation() default RegexValidator.class;
    String regex();
    String message() default "Regex not valid!";
}
