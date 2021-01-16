package com.validation.display;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabelErrorDisplay {
    private final String VALIDATION_MSG = "validation-message";
    private final Map<Node, List<Label>> nodeListMap = new HashMap<>();
    private static final LabelErrorDisplay instance = new LabelErrorDisplay();

    private LabelErrorDisplay() {}

    public static LabelErrorDisplay getInstance() {
        return instance;
    }

    public ArrayList<Node> getAllNodes(Parent root) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        addAllDescendents(root, nodes);
        return nodes;
    }

    private void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            nodes.add(node);
            if (node instanceof Parent)
                addAllDescendents((Parent)node, nodes);
        }
    }

    public void initialize(Node node) {
        List<Label> list = nodeListMap.get(node);
        if (list == null) {
            list = new ArrayList<>();
            List<Node> listNode = getAllNodes((Parent) node);
            for (Node el: listNode) {
                if (el instanceof Label) {
                    if (el.getStyleClass().contains(VALIDATION_MSG)) {
                        ((Label) el).setTextFill(Color.web("#eb4034"));
                        list.add((Label) el);
                    }
                }
            }
            nodeListMap.put(node, list);
        }
    }

    public List<Label> getLabelForNode(Node node) {
        List<Label> list = nodeListMap.get(node);
        if (list == null) {
            initialize(node);
            list = nodeListMap.get(node);
        }
        return list;
    }

    public void displayErrorLabel(Node node, String idNode, Boolean isError, String msg) {
        List<Label> list = getLabelForNode(node);
        for (Label label:
             list) {
            if (label.getLabelFor().getId().equals(idNode)) {
                label.setText(msg);
                label.setVisible(isError);
            }
        }
    }
}
