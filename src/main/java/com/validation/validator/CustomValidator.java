package com.validation.validator;

import com.validation.FXAbstractValidator;
import com.validation.annotations.FXValidation;
import com.validation.exceptions.ValidationException;
import javafx.scene.control.TextInputControl;

import java.util.Arrays;
import java.util.List;

public class CustomValidator extends FXAbstractValidator<TextInputControl, FXValidation> {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CustomValidator(TextInputControl control, FXValidation annotation) {
        super(control, annotation);
    }

    @Override
    public void validate(TextInputControl control, FXValidation annotation) throws ValidationException {
        setMessage(annotation.message());
        this.validate(control);
    }

    public CustomValidator(String message) {
        this.message = message;
    }

    @Override
    public void validate(TextInputControl control) throws ValidationException {
        List<String> validString = Arrays.asList("Design Pattern", "KTPM", "HCMUS", "FIT");
        if (control.isDisabled()) {
            this.isValid.set(true);
            return;
        }
        if (!control.isVisible()) {
            this.isValid.set(true);
            return;
        }
        this.isValid.set(validString.contains(control.getText()));
        if(!this.isValid.get()) {
            throw new ValidationException(annotation.message());
        }
    }
}
