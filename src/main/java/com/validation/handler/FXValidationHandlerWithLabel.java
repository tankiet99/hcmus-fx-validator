package com.validation.handler;

import com.validation.FXAbstractValidator;
import com.validation.ValidatorContext;
import com.validation.ValidatorFactory;
import com.validation.display.DialogErrorDisplay;
import com.validation.display.LabelErrorDisplay;
import com.validation.exceptions.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FXValidationHandlerWithLabel extends FXValidationHandler {
    public FXValidationHandlerWithLabel(Class controller, Node root) {
        super(controller, root);
    }

    @Override
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

    @Override
    public void addValidate(String validatorType, TextField tf, List<String> args) {
        this.mapValidator.put(tf, ValidatorFactory.getValidator(validatorType, args));
    }

    @Override
    protected void doValidate(FXAbstractValidator validator, TextField textField, Annotation annotation, String idNode, String msg) {
        try {
            // Dung mau strategy
            new ValidatorContext(validator).executeStrategy(textField, annotation);
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
                LabelErrorDisplay.getInstance().displayErrorLabel(root, idNode, true, msgErr);
            } else {
                LabelErrorDisplay.getInstance().displayErrorLabel(root, idNode, false, null);
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
            LabelErrorDisplay.getInstance().displayErrorLabel(root, idNode, true, msgErr);
        }
    }
}
