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

public class FXValidationHandlerWithDialog extends FXValidationHandler {
    public FXValidationHandlerWithDialog(Class controller, Node root) {
        super(controller, root);
    }

    @Override
    public void handle() {
        Class<?> aClazz = controller;
        Field[] fields = aClazz.getDeclaredFields();
        for(Field field: fields){
            Annotation[] annotations = (Annotation[]) field.getAnnotations();
            int totalValidator = 0;
            for (Annotation annotation: annotations){
                if (annotation instanceof FXML) continue;
                totalValidator++;
            }
            DialogErrorDisplay.getInstance().setTotalValidator(DialogErrorDisplay.getInstance().getTotalValidator() + totalValidator);
        }
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
        DialogErrorDisplay.getInstance().setTotalValidator(DialogErrorDisplay.getInstance().getTotalValidator() + 1);
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
                    DialogErrorDisplay.getInstance().display(textField, msgErr);
                } else {
                    DialogErrorDisplay.getInstance().display(textField, null);
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
                DialogErrorDisplay.getInstance().display(textField, msgErr);
            }
    }
}
