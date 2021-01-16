package com.validation;

import com.validation.exceptions.ValidationException;
import javafx.scene.control.TextInputControl;

import java.lang.annotation.Annotation;

public class ValidatorContext {
    private FXAbstractValidator strategy;

    public ValidatorContext(FXAbstractValidator strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy(TextInputControl tf, Annotation annotation) throws ValidationException {
        if (annotation == null) {
            strategy.validate(tf);
        } else {
            strategy.validate(tf, annotation);
        }
    }
}
