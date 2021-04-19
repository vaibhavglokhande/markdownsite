package com.markdownsite.integration.models;

import com.google.gson.Gson;
import com.markdownsite.integration.enums.TreeOperationErrorCode;
import com.markdownsite.integration.exceptions.TreeOperationException;
import com.markdownsite.integration.interfaces.SimpleTraverseMode;
import com.markdownsite.integration.interfaces.Tree;
import com.markdownsite.integration.interfaces.TreeTraverseMode;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * SimpleNavigableTree provides a simple data structure with hierarchical relationship of nodes.
 * <p>The tree is located at single root node and each node can have multiple children nodes. </p>
 */
public class SimpleTree<T extends Node<G>, G> implements Tree<T, G> {

    @Getter
    private T rootNode;

    public SimpleTree(T rootNode) {
        this.rootNode = rootNode;
    }

    @Override
    public T getParent(T node) {
        if (node.getParent() == null)
            return null;
        return (T) node.getParent();
    }

    @Override
    public List<T> getChildren(T node) {
        return (List<T>) node.getChildren();
    }

    /**
     * {@inheritDoc}
     * <p>This implementation creates and attach a new child node based on the provided node and returns it.</p>
     */
    @Override
    public T addNode(T parentNode, T childNode) {
        Assert.notNull(parentNode, "Parent node is null.");
        Assert.notNull(childNode, "Child node is null.");
        synchronized (this) {
            // Make a deep copy of the object
            // Set parent-child relationship.
            childNode.setParent(parentNode);
            // Add the node to the tree.
            parentNode.addChild(childNode);
            return childNode;
        }
    }

    @Override
    public List<T> addNodes(T parentNode, List<T> childrenNodes) {
        Assert.notNull(childrenNodes, "Children nodes are null.");
        List<T> attachedChildrenNodes = new ArrayList<>();
        childrenNodes.forEach(childNode -> attachedChildrenNodes.add(addNode(parentNode, childNode)));
        return attachedChildrenNodes;
    }

    @Override
    public List<T> traverse(T node, TreeTraverseMode simpleTraverseMode) {
        throw new RuntimeException("Method not yet implemented.");
    }

    @Override
    public T search(G value) {
        throw new RuntimeException("Method not yet implemented.");
    }

    @Override
    public T search(Predicate<T> predicate) {
        boolean present = predicate.test(rootNode);
        if(present)
            return rootNode;
        return null;
    }

    @Override
    public Stream<T> stream() {
        return traverse(rootNode, SimpleTraverseMode.BREADTH_FIRST).stream();
    }

    /**
     * {@inheritDoc}
     *
     * <p><b>Node deletion strategy:</b> Deletes the node from the tree only if it is leaf node. For any other node, {@link TreeOperationException} exception will be generated.</p>
     *
     * @throws TreeOperationException when the node is not leaf node.
     */
    @Override
    public void delete(T node) throws TreeOperationException {
        if (!isLeafNode(node)) {
            throw new TreeOperationException(TreeOperationErrorCode.NODE_DELETE_EXCEPTION);
        }
        T parent = getParent(node);
        // In case of root node, parent will be null.
        if (parent == null) {
            // Delete the root node of the tree.
            rootNode = null;
            return;
        }

        parent.getChildren().remove(node);
        node.setParent(null);
    }

    private boolean isLeafNode(T node) {
        return node.getChildren().isEmpty();
    }
}
