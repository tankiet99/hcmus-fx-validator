package com.validation;

import com.validation.annotations.FXRegex;
import com.validation.annotations.FXRequired;
import com.validation.exceptions.ValidationException;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.TextInputControl;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

public class RegexValidator extends FXAbstractValidator<TextInputControl, FXRegex>{
    public RegexValidator() {
    }

    public RegexValidator(TextInputControl control, FXRegex annotation) {
        super(control, annotation);
    }

    @Override
    public void validate(TextInputControl control, FXRegex annotation) throws ValidationException {
        if (control.isDisabled()) {
            this.isValid.set(true);
            return;
        }
        if (!control.isVisible()) {
            this.isValid.set(true);
            return;
        }

        String regex = annotation.regex();
        String text = control.getText();
        boolean valid = Pattern.matches(regex,text);
        System.out.println("đã vào hàm regex validate");

        if(!valid)
        {
            throw new ValidationException(annotation.message());
        }
    }
}
