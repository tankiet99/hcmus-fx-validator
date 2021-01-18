package com.validation;

import com.validation.exceptions.ValidationException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Control;

import java.lang.annotation.Annotation;

public abstract class FXAbstractValidator<T extends Control, A extends Annotation> {
    protected T control;

    protected A annotation;

    protected String message = "error";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    protected final BooleanProperty isValid = new SimpleBooleanProperty(false);

    public FXAbstractValidator() {
        this.message="error";
    }

    public FXAbstractValidator(T control, A annotation) {
        this.control = control;
        this.annotation = annotation;
    }

    public abstract void validate(T control, A annotation) throws ValidationException;

    public abstract void validate(T control) throws ValidationException;


    public void validate() throws ValidationException {
        this.validate(this.control, this.annotation);
    }

    public BooleanProperty isValidProperty() {
        return isValid;
    }

    public T getControl() {
        return control;
    }

    public void setControl(T control) {
        this.control = control;
    }

    public A getAnnotation() {
        return annotation;
    }

    public void setAnnotation(A annotation) {
        this.annotation = annotation;
    }
}
