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

public class FXValidationHandler {
    private Class controller;

    private Node root;

    private Map<String, Map<Annotation, String>> mapMessage;

    private Map<TextField, FXAbstractValidator> mapValidator;

    private String validatorType;

    private String displayType;

    public FXValidationHandler() {
    }

    public FXValidationHandler(Class controller, Node root, String displayType) {
        this.controller = controller;
        this.root = root;
        this.displayType = displayType;
        this.mapMessage = new HashMap<>();
        this.mapValidator = new HashMap<>();
    }

    public void handle() {
        Class<?> aClazz = controller;
        Field[] fields = aClazz.getDeclaredFields();
        if ("DIALOG".equals(displayType)) {
            for(Field field: fields){
                Annotation[] annotations = (Annotation[]) field.getAnnotations();
                int totalValidator = 0;
                for (Annotation annotation: annotations){
                    if (annotation instanceof FXML) continue;
                    totalValidator++;
                }
                DialogErrorDisplay.getInstance().setTotalValidator(DialogErrorDisplay.getInstance().getTotalValidator() + totalValidator);
            }

        }
        for(Field field: fields){
            Annotation[] annotations = (Annotation[]) field.getAnnotations();
            String idNode = field.getName();


            for (Annotation annotation: annotations){
                getValidator(annotation, idNode);
            }
        }
    }

    public void addValidate(String validatorType, TextField tf, List<String> args) {
//        doValidate(ValidatorFactory.getValidator(validatorType, args), tf, null, tf.getId(), args.get(args.size() - 1));
        this.mapValidator.put(tf, ValidatorFactory.getValidator(validatorType, args));
        if ("DIALOG".equals(displayType)) {
            DialogErrorDisplay.getInstance().setTotalValidator(DialogErrorDisplay.getInstance().getTotalValidator() + 1);
        }
    }

    public void executeValidate() {
        for (Map.Entry el:
             this.mapValidator.entrySet()) {
            doValidate(((FXAbstractValidator) el.getValue()), ((TextField) el.getKey()), null,
                    ((TextField) el.getKey()).getId(),
                    ((FXAbstractValidator) el.getValue()).getMessage());
        }
    }

    private void getValidator(Annotation annotation, String idNode) {
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

    private void doValidate(FXAbstractValidator validator, TextField textField, Annotation annotation, String idNode, String msg) {
        try {
            // Dung mau strategy
            new ValidatorContext(validator).executeStrategy(textField, annotation);
            DialogErrorDisplay.getInstance().setTotalValidator(DialogErrorDisplay.getInstance().getTotalValidator() - 1);
            if (mapMessage.get(idNode) != null) {
                mapMessage.get(idNode).remove(annotation);
            }
            String msgErr = "";
            if (mapMessage.get(idNode) != null) {
                for (Map.Entry el: mapMessage.get(idNode).entrySet()) {
                    msgErr = msgErr.concat(((String) el.getValue()) + " | ");
                }
            }
            if ("DIALOG".equals(displayType)) {
                if (!msgErr.equals("")) {
                    DialogErrorDisplay.getInstance().display(textField, msgErr);
                }
            } if ("LABEL".equals(displayType)) {
                if (!msgErr.equals("")) {
                    LabelErrorDisplay.getInstance().displayErrorLabel(root, idNode, true, msgErr);
                } else {
                    LabelErrorDisplay.getInstance().displayErrorLabel(root, idNode, false, null);
                }
            }
        } catch (ValidationException e) {
            if (mapMessage.get(idNode) == null) {
                mapMessage.put(idNode, new HashMap<>());
                mapMessage.get(idNode).put(annotation, msg);
            } else {
                mapMessage.get(idNode).put(annotation, msg);
            }
            String msgErr = "";
            for (Map.Entry el: mapMessage.get(idNode).entrySet()) {
                msgErr = msgErr.concat(((String) el.getValue()) + " | ");
            }
            if ("DIALOG".equals(displayType)) {
                DialogErrorDisplay.getInstance().display(textField, msgErr);
            } if ("LABEL".equals(displayType)) {
                LabelErrorDisplay.getInstance().displayErrorLabel(root, idNode, true, msgErr);
            }
        }
    }
}
