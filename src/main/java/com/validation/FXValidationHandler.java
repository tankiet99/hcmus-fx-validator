package com.validation;

import javafx.scene.Node;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class FXValidationHandler {
    public static void handle(Class controller, Node root) {
        Class<?> aClazz = controller;
        Field[] fields = aClazz.getDeclaredFields();
        for(Field field: fields){
            Annotation[] annotations = (Annotation[]) field.getAnnotations();
            for (Annotation annotation: annotations){
                System.out.println(annotation.annotationType().getName());
            }
        }
    }
}
