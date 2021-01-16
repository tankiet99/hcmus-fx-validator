package com.validation.display;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogErrorDisplay {
    private static DialogErrorDisplay instance;

    private Map<TextField, String> errors = new HashMap<>();

    private int totalValidator = 0;

    private int currentValidator = 0;

    private DialogErrorDisplay() {}

    public static DialogErrorDisplay getInstance() {
        if (instance == null) {
            instance = new DialogErrorDisplay();
        }
        return instance;
    }

    public int getCurrentValidator() {
        return currentValidator;
    }

    public void setCurrentValidator(int currentValidator) {
        this.currentValidator = currentValidator;
    }

    public int getTotalValidator() {
        return totalValidator;
    }

    public void setTotalValidator(int totalValidator) {
        System.out.println("Set total " + totalValidator);
        this.totalValidator = totalValidator;
    }

    public void addValidator(TextField tf, String msg) {
        this.errors.put(tf, msg);
        this.currentValidator++;
    }

    private String format() {
        StringBuilder result = new StringBuilder("");
        for (Map.Entry msg:
             errors.entrySet()) {
            result.append(((TextField) msg.getKey()).getText() + ": " + msg.getValue() + '\n');
        }

        return result.toString();
    }

    public void display(TextField tf, String msg) {
        getInstance().addValidator(tf, msg);
        System.out.println(getInstance().getCurrentValidator() + " " + getInstance().getTotalValidator());
        if (getInstance().getCurrentValidator() >= getInstance().getTotalValidator()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nhập liệu");
            alert.setHeaderText("Nội dung nhập không hợp lệ");
            alert.setContentText(format());

            alert.showAndWait();
            // reset instance
            instance = new DialogErrorDisplay();
        }
    }
}
