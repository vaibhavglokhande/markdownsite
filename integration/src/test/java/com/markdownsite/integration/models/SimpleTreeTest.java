package com.markdownsite.integration.models;

import com.markdownsite.integration.enums.TreeOperationErrorCode;
import com.markdownsite.integration.exceptions.TreeOperationException;
import com.markdownsite.integration.interfaces.SimpleTraverseMode;
import com.markdownsite.integration.interfaces.Tree;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
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
    void traverse() throws TreeOperationException {
        Node<String> rootNode = new Node<>();
        rootNode.setValue("root-node");

        Tree<Node<String>, String> tree = buildTestTree(rootNode);
        List<Node<String>> traversedList = tree.traverse(rootNode, SimpleTraverseMode.BREADTH_FIRST);
        assertEquals(5, traversedList.size());
        // Expected sequence.
        assertEquals("root-node", traversedList.get(0).getValue());
        assertEquals("child-node", traversedList.get(1).getValue());
        assertEquals("child-node-2", traversedList.get(2).getValue());
        assertEquals("child-node-3", traversedList.get(3).getValue());
        assertEquals("child-node-4", traversedList.get(4).getValue());
    }

    @Test
    void traverseDepthFirst() throws TreeOperationException {
        Node<String> rootNode = new Node<>();
        rootNode.setValue("root-node");

        Tree<Node<String>, String> tree = buildTestTree(rootNode);
        List<Node<String>> traversedList = tree.traverse(rootNode, SimpleTraverseMode.DEPTH_FIRST);
        assertEquals(5, traversedList.size());
        // Expected Sequence
        assertEquals("root-node", traversedList.get(0).getValue());
        assertEquals("child-node", traversedList.get(1).getValue());
        assertEquals("child-node-3", traversedList.get(2).getValue());
        assertEquals("child-node-2", traversedList.get(3).getValue());
        assertEquals("child-node-4", traversedList.get(4).getValue());
    }

    @Test
    void testConvert() throws TreeOperationException {
        Node<String> rootNode = new Node<>();
        rootNode.setValue("root-node");

        SimpleTree<Node<String>, String> nodeStringTree = (SimpleTree<Node<String>, String>) buildTestTree(rootNode);
        long originalTreeSize = nodeStringTree.stream().count();
        Tree<SourceNavigationNode<Integer>, String> convert = nodeStringTree.convert(stringNode -> {
            SourceNavigationNode<Integer> integerSourceNavigationNode = new SourceNavigationNode<>();
            integerSourceNavigationNode.setValue(stringNode.getValue());
            MarkdownElement<Integer> integerMarkdownElement = new MarkdownElement<>();
            integerMarkdownElement.setIdentifier(1);
            integerMarkdownElement.setContent("New Content");
            integerSourceNavigationNode.setMarkdownElement(integerMarkdownElement);
            return integerSourceNavigationNode;
        });
        assertNotNull(convert);
        assertEquals(originalTreeSize, convert.stream().count());
    }

    private Tree<Node<String>, String> buildTestTree(Node<String> rootNode) throws TreeOperationException {
        Tree<Node<String>, String> tree = new SimpleTree<>(rootNode);

        Node<String> childNode = new Node<>();
        childNode.setValue("child-node");

        Node<String> childNode2 = new Node<>();
        childNode2.setValue("child-node-2");

        Node<String> childNode3 = new Node<>();
        childNode3.setValue("child-node-3");

        Node<String> childNode4 = new Node<>();
        childNode4.setValue("child-node-4");

        tree.addNodes(rootNode, Stream.of(childNode, childNode2).toList());
        tree.addNode(childNode, childNode3);
        tree.addNode(childNode2, childNode4);
        return tree;
    }

    @Test
    void search() throws TreeOperationException {
        Node<String> rootNode = new Node<>();
        rootNode.setValue("root-node");

        Tree<Node<String>, String> tree = buildTestTree(rootNode);
        Node<String> searchForNode4 = tree.search("child-node-4");
        assertNotNull(searchForNode4);
        assertEquals("child-node-4", searchForNode4.getValue());

        Node<String> searchedChildNode = tree.search(node -> node.getValue().equalsIgnoreCase("child-node"));
        assertNotNull(searchedChildNode);
        assertEquals("child-node", searchedChildNode.getValue());

        Node<String> search = tree.search("not-present");
        assertNull(search);

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

        assertEquals(TreeOperationErrorCode.NODE_DELETE_EXCEPTION, treeOperationException.getErrorCode());

    }
}