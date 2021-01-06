package org.example;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.validation.*;
import com.validation.annotations.FXRequired;
import com.validation.annotations.FXString;
import com.validation.annotations.FXValidation;
import com.validation.exceptions.ValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class FormSubmitController {
    @FXML
    AnchorPane anchorPane;

    @FXML
    public Button primaryButton;

    @FXML
    @FXValidation(validation = CustomValidator.class, message = "Fail")
    TextField tf1;

    @FXML
    @FXRequired(message = "required field!")
    TextField tf2;

    @FXML
    @FXRequired(message = "field is required!")
    @FXString(max = 10, min = 5, message = "độ dài filed không hợp lệ")
    TextField tf3;

    @FXML
    TextField tf4;

    @FXML
    Label lb1;

    @FXML
    private void submit() throws IOException{
//        App.setRoot("secondary");
        new FXValidationHandler(FormSubmitController.class, anchorPane).handle();
//        Validator.valid("STRING", "dkfd", 2, 3, tf1);

      /*  Class<?> aClazz = FormSubmitController.class;

        //lấy cái Field khai báo là tf3 ra
        Field field3 = aClazz.getDeclaredField("tf3");
        //lấy annotation FXString của Filed
        Annotation an = (Annotation) field3.getDeclaredAnnotation(FXString.class);

        // tạo cái String validator để validate
        StringValidator rs = new StringValidator();

        // gọi hàm validate tf3 với cái annotation là FXString an
        rs.validate(tf3, (FXString) an);

        // tương tự vs các field khác
        //...

        // Chúng ta cũng có thể sử dụng
        // StringValidator stringValidator = new StringValidator(tf3, (FXString) an);
        // stringValidator.validate();*/
//        new FXValidationHandler().handle(FormSubmitController.class, anchorPane);
    }
}
