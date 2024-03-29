package com.validation.validator;

import com.validation.FXAbstractValidator;
import com.validation.annotations.FXRegex;
import com.validation.exceptions.ValidationException;
import javafx.scene.control.TextInputControl;

import java.util.regex.Pattern;

public class RegexValidator extends FXAbstractValidator<TextInputControl, FXRegex> {
    private String regex="";
    public RegexValidator() {
        super();
    }

    public RegexValidator(String regex, String message) {
        this.regex = regex;
        this.message = message;
    }
    public RegexValidator(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public RegexValidator(TextInputControl control, FXRegex annotation) {
        super(control, annotation);
    }

    @Override
    public void validate(TextInputControl control, FXRegex annotation) throws ValidationException {
        setRegex(annotation.regex());
        this.validate(control);
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

        String text = control.getText();
        boolean valid = Pattern.matches(this.regex,text);

        if(!valid)
        {
            throw new ValidationException(this.message);
        }
    }
}
