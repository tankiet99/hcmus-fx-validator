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
    private String regex;
    public RegexValidator() {
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
        System.out.println("đã vào hàm regex validate");

        if(!valid)
        {
            throw new ValidationException(this.message);
        }
    }
}
