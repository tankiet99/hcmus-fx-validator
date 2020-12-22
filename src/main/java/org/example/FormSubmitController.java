package org.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FormSubmitController {
    @FXML
    public Button primaryButton;

    @FXML
    TextField tf1;

    @FXML
    TextField tf2;

    @FXML
    TextField tf3;

    @FXML
    TextField tf4;

    @FXML
    private void submit() throws IOException {
        App.setRoot("secondary");
    }
}
