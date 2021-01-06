package com.validation;

import com.validation.annotations.FXRequired;
import com.validation.exceptions.ValidationException;
import javafx.scene.control.TextInputControl;

public class RequiredValidator extends FXAbstractValidator<TextInputControl, FXRequired> {
    private String message = "This field must not be null!";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RequiredValidator() {
        super();
    }

    public RequiredValidator(String message) {
        this.message = message;
    }

    public RequiredValidator(TextInputControl control, FXRequired annotation) {
        super(control, annotation);
    }

    @Override
    public void validate(TextInputControl control, FXRequired annotation) throws ValidationException {
        this.message = annotation.message();
        validate(control);
    }

    @Override
    public void validate(TextInputControl control) throws ValidationException {
        if (control.isDisabled()) {
            this.isValid.set(true);
            return;
        }
        if (!control.isVisible()) {
            this.isValid.set(true);
            return;
        }

        boolean valid = control.getText().length() > 0;
        this.isValid.set(valid);

        if (!valid) {
            throw new ValidationException(this.message);
        }
    }
}
