package org.example;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.validation.FXAbstractValidator;
import com.validation.RequiredValidator;
import com.validation.StringValidator;
import com.validation.annotations.FXRequired;
import com.validation.annotations.FXString;
import com.validation.exceptions.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;


public class FormSubmitController {
    @FXML
    public Button primaryButton;

    @FXML
    TextField tf1;

    @FXML
    @FXRequired(message = "required field!")
    TextField tf2;

    @FXML
    @FXRequired(message = "field is required!")
    @FXString(max = 10, min = 2, message = "độ dài filed không hợp lệ")
    TextField tf3;

    @FXML
    TextField tf4;

    @FXML
    private void submit() throws IOException, NoSuchFieldException, ValidationException {
        App.setRoot("secondary");

        Class<?> aClazz = FormSubmitController.class;

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
        // stringValidator.validate();
    }
}
