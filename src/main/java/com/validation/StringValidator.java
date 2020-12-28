package com.validation;

import com.validation.annotations.FXString;
import com.validation.exceptions.ValidationException;
import javafx.scene.control.TextInputControl;

import java.lang.annotation.Annotation;

public class StringValidator extends FXAbstractValidator<TextInputControl, FXString>{
    public StringValidator() {
        super();
    }

    public StringValidator(TextInputControl control, FXString annotation) {
        super(control, annotation);
    }

    @Override
    public void validate(TextInputControl control, FXString annotation) throws ValidationException {
        int length = control.getText().toString().length();
        boolean valid = true;
        if(length<annotation.min()) {
            valid = false;
            this.isValid.set(valid);
            throw new ValidationException(annotation.message());
        }

        if (length>annotation.max()) {
            valid = false;
            this.isValid.set(valid);
            throw new ValidationException(annotation.message());
        }
    }
}
