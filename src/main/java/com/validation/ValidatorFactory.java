package com.validation;

import com.validation.validator.*;

import java.util.List;

public class ValidatorFactory {
    public static FXAbstractValidator getValidator(String validatorType, List<String> args) {
        try {
            if (validatorType == null) {
                return null;
            }
            if (validatorType.equals("REQUIRED")) {
                return new RequiredValidator(args.get(0));
            }
            if (validatorType.equals("STRING")) {
                return new StringValidator(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)), args.get(2));
            }
            if (validatorType.equals("REGEX")) {
                return new RegexValidator(args.get(0), args.get(1));
            }
            if (validatorType.equals("NUMBER")) {
                return new NumberValidator(Double.parseDouble(args.get(0)), Double.parseDouble(args.get(1)), args.get(2));
            }
            if (validatorType.equals("INTEGER")) {
                return new IntegerValidator(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)), args.get(2));
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
