package com.markdownsite.integration.models;

import com.markdownsite.integration.exceptions.TreeOperationException;
import com.markdownsite.integration.interfaces.Tree;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SimpleTreeTest {

    @Test
    void getParent() throws TreeOperationException {
        Node<String> rootNode = new Node<>();
        rootNode.setValue("root-node");
        Tree<Node<String>, String> tree = new SimpleTree<>(rootNode);

        Node<String> childNode = new Node<>();
        childNode.setValue("child-node");

        Node<String> newChildNode = tree.addNode(rootNode, childNode);

        assertEquals(rootNode, newChildNode.getParent());


    }

    @Test
    void getChildren() throws TreeOperationException {
        Node<String> rootNode = new Node<>();
        rootNode.setValue("root-node");
        Tree<Node<String>, String> tree = new SimpleTree<>(rootNode);

        Node<String> childNode = new Node<>();
        childNode.setValue("child-node");

        Node<String> newChildNode = tree.addNode(rootNode, childNode);

        assertEquals(newChildNode, rootNode.getChildren().get(0));
    }


    @Test
    void addNodes() throws TreeOperationException {
        Node<String> rootNode = new Node<>();
        rootNode.setValue("root-node");
        Tree<Node<String>, String> tree = new SimpleTree<>(rootNode);

        Node<String> childNode = new Node<>();
        childNode.setValue("child-node");

        Node<String> childNode2 = new Node<>();
        childNode2.setValue("child-node-2");


        List<Node<String>> nodes = tree.addNodes(rootNode, Stream.of(childNode, childNode2).toList());

        assertEquals(nodes, rootNode.getChildren());
    }

    @Test
    void traverse() {
    }

    @Test
    void search() {
    }

    @Test
    void testDelete() throws TreeOperationException {
        Node<String> rootNode = new Node<>();
        rootNode.setValue("root-node");
        Tree<Node<String>, String> tree = new SimpleTree<>(rootNode);

        Node<String> childNode = new Node<>();
        childNode.setValue("child-node");

        Node<String> newChildNode = tree.addNode(rootNode, childNode);

        tree.delete(newChildNode);
        assertEquals(0, rootNode.getChildren().size());
    }

    @Test
    void testDeleteRootNode() throws TreeOperationException {
        Node<String> rootNode = new Node<>();
        rootNode.setValue("root-node");
        Tree<Node<String>, String> tree = new SimpleTree<>(rootNode);
        tree.delete(rootNode);
        assertNull(((SimpleTree) tree).getRootNode());
    }

    @Test
    void testDeleteNonLeafNode() throws TreeOperationException {
        Node<String> rootNode = new Node<>();
        rootNode.setValue("root-node");
        Tree<Node<String>, String> tree = new SimpleTree<>(rootNode);

        Node<String> childNode = new Node<>();
        childNode.setValue("child-node");

        tree.addNode(rootNode, childNode);

        TreeOperationException treeOperationException = assertThrows(TreeOperationException.class, () -> tree.delete(rootNode));

        assertEquals(com.markdownsite.integration.enums.TreeOperationException.NODE_DELETE_EXCEPTION, treeOperationException.getErrorCode());

    }
}