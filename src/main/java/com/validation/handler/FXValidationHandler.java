package com.validation.handler;

import com.validation.*;
import com.validation.annotations.FXRegex;
import com.validation.annotations.FXRequired;
import com.validation.annotations.FXString;
import com.validation.annotations.FXValidation;
import com.validation.display.DialogErrorDisplay;
import com.validation.display.LabelErrorDisplay;
import com.validation.exceptions.ValidationException;
import com.validation.FXAbstractValidator;
import com.validation.validator.RegexValidator;
import com.validation.validator.RequiredValidator;
import com.validation.validator.StringValidator;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class FXValidationHandler {
    protected Class controller;

    protected Node root;

    protected Map<String, Map<Annotation, String>> mapMessage;

    protected Map<TextField, FXAbstractValidator> mapValidator;

    public FXValidationHandler() {
    }

    public FXValidationHandler(Class controller, Node root) {
        this.controller = controller;
        this.root = root;
        this.mapMessage = new HashMap<>();
        this.mapValidator = new HashMap<>();
    }

    public abstract void handle();

    public abstract void addValidate(String validatorType, TextField tf, List<String> args);

    protected void getValidator(Annotation annotation, String idNode) {
        if (annotation instanceof FXRequired) {
            TextField textField = (TextField) root.lookup("#" + idNode);
            RequiredValidator requiredValidator = new RequiredValidator();
            doValidate(requiredValidator, textField, annotation, idNode, ((FXRequired) annotation).message());
        } else if (annotation instanceof FXString) {
            TextField textField = (TextField) root.lookup("#" + idNode);
            StringValidator stringValidator = new StringValidator();
            doValidate(stringValidator, textField, annotation, idNode, ((FXString) annotation).message());
        }else if (annotation instanceof FXRegex) {
            TextField textField = (TextField) root.lookup("#" + idNode);
            RegexValidator regexValidator = new RegexValidator();
            doValidate(regexValidator, textField, annotation, idNode, ((FXRegex) annotation).message());
        } else if (annotation instanceof FXValidation) {
            TextField textField = (TextField) root.lookup("#" + idNode);
            FXAbstractValidator obj = null;
            try {
                obj = ((FXValidation) annotation).validation().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            doValidate(obj,textField, annotation, idNode, ((FXValidation) annotation).message());
        }
    }

    protected abstract void doValidate(FXAbstractValidator validator,
                                       TextField textField,
                                       Annotation annotation,
                                       String idNode, String msg);

    public final void executeValidate() {
        handle();
        if (this.mapValidator.size() == 0) return;
        for (Map.Entry el:
                this.mapValidator.entrySet()) {
            doValidate(((FXAbstractValidator) el.getValue()), ((TextField) el.getKey()), null,
                    ((TextField) el.getKey()).getId(),
                    ((FXAbstractValidator) el.getValue()).getMessage());
        }
    }
}
