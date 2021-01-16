package com.validation.validator;

import com.validation.FXAbstractValidator;
import com.validation.annotations.FXString;
import com.validation.exceptions.ValidationException;
import javafx.scene.control.TextInputControl;

import java.lang.annotation.Annotation;

public class StringValidator extends FXAbstractValidator<TextInputControl, FXString> {
    private int min=0;
    private int max =100;
    private String message = "length of field is not valid!";

    public StringValidator(int min, int max, String message) {
        this.min = min;
        this.max = max;
        this.message = message;
    }

    public StringValidator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    private void getAnnotation(Annotation annotation){
        this.min =((FXString) annotation).min();
        this.max = ((FXString) annotation).max();
        this.message = ((FXString) annotation).message();
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StringValidator() {
        super();
    }

    public StringValidator(TextInputControl control, FXString annotation) {
        super(control, annotation);
    }

    @Override
    public void validate(TextInputControl control, FXString annotation) throws ValidationException {
        getAnnotation(annotation);
        validate(control);
    }

    @Override
    public void validate(TextInputControl control) throws ValidationException {
        int length = control.getText().toString().length();
        boolean valid = true;
        if(length<this.min) {
            valid = false;
            this.isValid.set(valid);
            throw new ValidationException(this.message);
        }

        if (length>this.max) {
            valid = false;
            this.isValid.set(valid);
            throw new ValidationException(this.message);
        }
    }
}
