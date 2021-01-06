package com.validation;

import com.validation.annotations.FXNumber;
import com.validation.exceptions.ValidationException;
import javafx.scene.control.TextInputControl;

public class NumberValidator extends FXAbstractValidator<TextInputControl, FXNumber>{
    public NumberValidator() {
        super();
    }

    public NumberValidator(TextInputControl control, FXNumber annotation) {
        super(control, annotation);
    }

    @Override
    public void validate(TextInputControl control, FXNumber annotation) throws ValidationException {
        if (control.isDisabled()) {
            this.isValid.set(true);
            return;
        }
        if (!control.isVisible()) {
            this.isValid.set(true);
            return;
        }
        boolean err = true;
        Number number = Double.parseDouble(control.getText());

        if (annotation.min() != Double.MIN_VALUE) {
            err = err && (number.doubleValue() >= annotation.min());
        }

        if (annotation.max() != Double.MAX_VALUE) {
            err = err && (number.doubleValue() <= annotation.max());
        }
        System.out.println(err);
        this.isValid.set(err);

        if (!err) {
            throw new ValidationException(annotation.message());
        }
    }
}
