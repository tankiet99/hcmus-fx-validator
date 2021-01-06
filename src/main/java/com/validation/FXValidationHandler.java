package com.validation;

import com.validation.annotations.FXRegex;
import com.validation.annotations.FXRequired;
import com.validation.annotations.FXString;
import com.validation.annotations.FXValidation;
import com.validation.exceptions.ValidationException;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FXValidationHandler {
    private Class controller;

    private Node root;

    private Map<String, Map<Annotation, String>> mapMessage;

    private String validatorType;

    public FXValidationHandler() {
    }

    public FXValidationHandler(Class controller, Node root) {
        this.controller = controller;
        this.root = root;
        this.mapMessage = new HashMap<>();
    }

    public void handle() {
        Class<?> aClazz = controller;
        Field[] fields = aClazz.getDeclaredFields();
        for(Field field: fields){
            Annotation[] annotations = (Annotation[]) field.getAnnotations();
            String idNode = field.getName();
            for (Annotation annotation: annotations){
                getValidator(annotation, idNode);
            }
        }
    }

    public void handle(String validatorType, TextField tf, String msg) {
//        doValidate(new ValidatorFactory().getValidator(validatorType), tf, tf.getId(), msg);
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
            validator.validate(textField, annotation);
            if (mapMessage.get(idNode) != null) {
                mapMessage.get(idNode).remove(annotation);
            }
            String msgErr = "";
            if (mapMessage.get(idNode) != null) {
                for (Map.Entry el: mapMessage.get(idNode).entrySet()) {
                    msgErr = msgErr.concat(((String) el.getValue()) + " | ");
                }
            }
            if (!msgErr.equals("")) {
                LabelErrorHandler.getInstance().displayErrorLabel(root, idNode, true, msgErr);
            } else {
                LabelErrorHandler.getInstance().displayErrorLabel(root, idNode, false, null);
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
            LabelErrorHandler.getInstance().displayErrorLabel(root, idNode, true, msgErr);
        }
    }
}
