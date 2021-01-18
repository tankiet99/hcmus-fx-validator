package com.validation.validator;

import com.validation.FXAbstractValidator;
import com.validation.annotations.FXInteger;
import com.validation.exceptions.ValidationException;
import javafx.scene.control.TextInputControl;

public class IntegerValidator extends FXAbstractValidator<TextInputControl, FXInteger> {
    public IntegerValidator() {
        super();
    }

    private int min;
    private int max;
    private String stringMessage;

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

    public String getStringMessage() {
        return stringMessage;
    }

    public void setStringMessage(String stringMessage) {
        this.stringMessage = stringMessage;
    }

    public IntegerValidator(int min, int max, String stringMessage) {
        this.min = min;
        this.max = max;
        this.stringMessage = stringMessage;
    }

    public IntegerValidator(TextInputControl control, FXInteger annotation) {
        super(control, (FXInteger) annotation);
    }

    @Override
    public void validate(TextInputControl control, FXInteger annotation) throws ValidationException {
        setMax(annotation.max());
        setMin(annotation.min());
        setStringMessage(annotation.message());
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
        boolean err = true;
        try {

            System.out.println(control.getText());
            int number = Integer.parseInt(control.getText());
            if (this.min != Integer.MIN_VALUE) {
                err = err && (number >= this.min);
            }

            if (this.max != Double.MAX_VALUE) {
                err = err && (number <= this.max);
            }
            System.out.println(err);
            this.isValid.set(err);
            if (!err) {
                throw new ValidationException(this.message);
            }
        }
        catch (Exception e){
            throw new ValidationException(this.message);
        }
    }
}
