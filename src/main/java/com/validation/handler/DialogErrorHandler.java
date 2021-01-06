package com.validation.handler;

import javafx.scene.control.Alert;

import java.util.List;

public class DialogErrorHandler {
    private String format(List<String> msgs) {
        StringBuilder result = new StringBuilder("");
        for (String msg:
             msgs) {
            result.append(msg + '\n');
        }

        return result.toString();
    }

    public void display(List<String> msgs) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Nhập liệu");
        alert.setHeaderText("Nội dung nhập không hợp lệ");
        alert.setContentText(format(msgs));

        alert.showAndWait();
    }
}
