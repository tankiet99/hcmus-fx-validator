package org.example;

import java.util.Arrays;

import com.validation.annotations.*;

import com.validation.handler.FXValidationHandler;
import com.validation.handler.FXValidationHandlerWithDialog;
import com.validation.handler.FXValidationHandlerWithLabel;
import com.validation.handler.FactoryHandlerProducer;
import com.validation.validator.CustomValidator;
import javafx.fxml.FXML;
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
//    @FXInteger(message = "Input value is not integer")
    TextField tf1;

    @FXML
//    @FXRequired(message = "required field!")
    //@FXNumber(min = 10,max = 100)
    @FXValidation(validation = CustomValidator.class, message = "k dc")
    TextField tf2;

    @FXML
    @FXRequired(message = "field is required!")
    @FXString(max = 10, min = 5, message = "độ dài filed không hợp lệ")
    TextField tf3;

    @FXML
    @FXRegex(regex = "^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$")
    TextField tf4;

    @FXML
    Label lb1;

    @FXML
    private void submit() {
//        App.setRoot("secondary");
        FXValidationHandler handler = FactoryHandlerProducer.getHandler("LABEL");
        handler.initialize(FormSubmitController.class, anchorPane);
        handler.addValidate("REQUIRED", tf1, Arrays.asList("Invalid"));
        handler.executeValidate();
//        Validator.valid("STRING", "dkfd", 2, 3, tf1);
//        new DialogErrorHandler().display(new ArrayList<String>(Arrays.asList("Độ dài không hợp lệ", "Email không phù hợp")));

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