package com.markdownsite.integration.models;

import com.markdownsite.integration.interfaces.NavigableTree;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SimpleNavigableTreeTest {

    @Test
    void getParent() {
        Node<String> rootNode = new Node<>();
        rootNode.setValue("root-node");
        NavigableTree<Node<String>, String> navigableTree = new SimpleNavigableTree<>(rootNode);

        Node<String> childNode = new Node<>();
        childNode.setValue("child-node");

        Node<String> newChildNode = navigableTree.addNode(rootNode, childNode);

        assertEquals(rootNode, newChildNode.getParent());


    }

    @Test
    void getChildren() {
        Node<String> rootNode = new Node<>();
        rootNode.setValue("root-node");
        NavigableTree<Node<String>, String> navigableTree = new SimpleNavigableTree<>(rootNode);

        Node<String> childNode = new Node<>();
        childNode.setValue("child-node");

        Node<String> newChildNode = navigableTree.addNode(rootNode, childNode);

        assertEquals(newChildNode, rootNode.getChildren().get(0));
    }


    @Test
    void addNodes() {
        Node<String> rootNode = new Node<>();
        rootNode.setValue("root-node");
        NavigableTree<Node<String>, String> navigableTree = new SimpleNavigableTree<>(rootNode);

        Node<String> childNode = new Node<>();
        childNode.setValue("child-node");

        Node<String> childNode2 = new Node<>();
        childNode2.setValue("child-node-2");


        List<Node<String>> nodes = navigableTree.addNodes(rootNode, Stream.of(childNode, childNode2).toList());

        assertEquals(nodes, rootNode.getChildren());
    }

    @Test
    void traverse() {
    }

    @Test
    void search() {
    }
}