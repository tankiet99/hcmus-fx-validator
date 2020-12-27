package com.validation;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabelErrorHandler {
    private static final String VALIDATION_MSG = "validation-message";
    private static Map<Node, List<Label>> nodeListMap = new HashMap<>();

    public static ArrayList<Node> getAllNodes(Parent root) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        addAllDescendents(root, nodes);
        return nodes;
    }

    private static void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            nodes.add(node);
            if (node instanceof Parent)
                addAllDescendents((Parent)node, nodes);
        }
    }

    private LabelErrorHandler() {

    }

    public static Map<Node, List<Label>> getNodeListMap() {
        return nodeListMap;
    }

    public static void initialize(Node node) {
        List<Label> list = nodeListMap.get(node);
        if (list == null) {
            list = new ArrayList<>();
            List<Node> listNode = getAllNodes((Parent) node);
            for (Node el: listNode) {
                if (el instanceof Label) {
                    if (el.getStyleClass().contains(VALIDATION_MSG)) {
                        list.add((Label) el);
                    }
                }
            }
            nodeListMap.put(node, list);
        }
    }

    public static List<Label> getLabelForNode(Node node) {
        List<Label> list = nodeListMap.get(node);
        if (list == null) {
            initialize(node);
            list = nodeListMap.get(node);
        }
        return list;
    }
}
