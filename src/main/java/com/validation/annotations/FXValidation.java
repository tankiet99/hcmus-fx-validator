package com.validation.annotations;

import com.validation.FXAbstractValidator;
import javafx.scene.control.Control;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FXValidation {
    public Class <? extends FXAbstractValidator> validation();
    /* public Class <? extends Control>[] applicableFor() default {}; */
    public String message() default "Validation failed!";
}
