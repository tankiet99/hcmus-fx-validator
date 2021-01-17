package com.validation.handler;

public class FactoryHandlerProducer {
    public static FXValidationHandler getHandler(String displayType) {
        if (displayType.equals("DIALOG")) {
            return new FXValidationHandlerWithDialog();
        }
        if (displayType.equals("LABEL")) {
            return new FXValidationHandlerWithLabel();
        }
        return null;
    }
}
