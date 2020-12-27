package org.example;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

import com.validation.FXValidationHandler;
import com.validation.LabelErrorHandler;
import com.validation.annotations.FXRequired;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class FormSubmitController {
    @FXML
    AnchorPane anchorPane;

    @FXML
    public Button primaryButton;

    @FXML
    TextField tf1;

    @FXML
    @FXRequired
    TextField tf2;

    @FXML
    @FXRequired(message = "hello")
    TextField tf3;

    @FXML
    TextField tf4;

    @FXML
    private void submit() throws IOException, NoSuchFieldException {
        FXValidationHandler.handle(FormSubmitController.class, anchorPane);
//        for (Node node : anchorPane.getChildren()) {
//            System.out.println(node);
//            if (node instanceof Label) {
//                // clear
//                System.out.println(((Label)node).getStyleClass() + "nodeeeee");
//            }
//        }
//        Class<?> aClazz = FormSubmitController.class;
//
//        Field field = aClazz.getDeclaredField("tf3");
//        Annotation an;
//        an = (Annotation) field.getDeclaredAnnotation(FXRequired.class);
//        FXRequired fxr = (FXRequired) an;
//        System.out.println(fxr.message());

//        Field[] fields = aClazz.getDeclaredFields();
//        for(Field field: fields){
//            Annotation[] annotations = (Annotation[]) field.getAnnotations();
//            for (Annotation annotation: annotations){
//                System.out.println(annotation.annotationType().getName());
//            }
//        }
        App.setRoot("secondary");
    }
}
