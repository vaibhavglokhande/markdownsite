package com.markdownsite.integration.models;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"parent"})
public class Node<G> {
    private Node<G> parent;
    private final List<Node<G>> children = new ArrayList<>();
    G value;

    public void addChild(Node<G> childNode) {
        this.children.add(childNode);
    }

}
