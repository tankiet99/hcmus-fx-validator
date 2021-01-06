package com.validation;

public class ValidatorFactory {
    public FXAbstractValidator getValidator(String validatorType) {
        if (validatorType == null) {
            return null;
        }
        if (validatorType.equals("REQUIRED")) {
            return new RequiredValidator();
        }
        if (validatorType.equals("STRING")) {
            return new RequiredValidator();
        }
        return null;
    }
}
