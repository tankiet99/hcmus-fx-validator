package com.validation;

import com.validation.annotations.FXRequired;
import com.validation.exceptions.ValidationException;
import javafx.scene.control.TextInputControl;

public class RequiredValidator extends FXAbstractValidator<TextInputControl, FXRequired> {
    public RequiredValidator() {
        super();
    }

    public RequiredValidator(TextInputControl control, FXRequired annotation) {
        super(control, annotation);
    }

    @Override
    public void validate(TextInputControl control, FXRequired annotation) throws ValidationException {
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
            throw new ValidationException(annotation.message());
        }
    }
}
