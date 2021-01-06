package com.validation;

import com.validation.annotations.FXValidation;
import com.validation.exceptions.ValidationException;
import javafx.scene.control.TextInputControl;

import java.util.Arrays;
import java.util.List;

public class CustomValidator extends FXAbstractValidator<TextInputControl, FXValidation> {

    @Override
    public void validate(TextInputControl control, FXValidation annotation) throws ValidationException {
        List<String> validString = Arrays.asList("Design Pattern", "KTPM", "HCMUS", "FIT");
        this.isValid.set(validString.contains(control.getText()));
        if(!this.isValid.get()) {
            throw new ValidationException(annotation.message());
        }
    }
}
