package com.validation.validator;

import com.validation.FXAbstractValidator;
import com.validation.annotations.FXNumber;
import com.validation.exceptions.ValidationException;
import javafx.scene.control.TextInputControl;

public class NumberValidator extends FXAbstractValidator<TextInputControl, FXNumber> {
    public NumberValidator() {
        super();
    }

    private Double min;
    private Double max;

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NumberValidator(Double min, Double max, String stringMessage) {
        this.min = min;
        this.max = max;
        this.message = stringMessage;
    }

    public NumberValidator(TextInputControl control, FXNumber annotation) {
        super(control, annotation);
    }

    @Override
    public void validate(TextInputControl control, FXNumber annotation) throws ValidationException {
        setMax(annotation.max());
        setMin(annotation.min());
        setMessage(annotation.message());
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
            Double number = Double.parseDouble(control.getText());
            if (this.min != Double.MIN_VALUE) {
                err = err && (number.doubleValue() >= this.min);
            }

            if (this.max != Double.MAX_VALUE) {
                err = err && (number.doubleValue() <= this.max);
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
