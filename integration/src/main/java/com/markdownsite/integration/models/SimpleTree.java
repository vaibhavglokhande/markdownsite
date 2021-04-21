package com.markdownsite.integration.models;

import com.markdownsite.integration.enums.TreeOperationErrorCode;
import com.markdownsite.integration.exceptions.TreeOperationException;
import com.markdownsite.integration.interfaces.SimpleTraverseMode;
import com.markdownsite.integration.interfaces.Tree;
import com.markdownsite.integration.interfaces.TreeTraverseMode;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.*;
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
    public List<T> traverse(T node, TreeTraverseMode simpleTraverseMode) throws TreeOperationException {
        Assert.notNull(node, "Traversal node cannot be null");
        Assert.notNull(simpleTraverseMode, "Traversal mode cannot be null.");
        if (SimpleTraverseMode.BREADTH_FIRST.equals(simpleTraverseMode)) {
            return breadthFirstTravel(node);
        }
        if (SimpleTraverseMode.DEPTH_FIRST.equals(simpleTraverseMode)) {
            return depthFirstTravel(node, simpleTraverseMode);
        }
        throw new TreeOperationException(TreeOperationErrorCode.TRAVERSAL_MODE_NOT_SUPPORTED);
    }

    private List<T> depthFirstTravel(T node, TreeTraverseMode simpleTraverseMode) throws TreeOperationException {
        List<T> nodeList = new ArrayList<>();
        nodeList.add(node);
        if (!node.getChildren().isEmpty()) {
            for (Node<G> childNode : node.getChildren()) {
                nodeList.addAll(traverse((T) childNode, simpleTraverseMode));
            }
        }
        return nodeList;
    }

    private List<T> breadthFirstTravel(T node) {
        List<T> nodeList = new ArrayList<>();
        Queue<T> travelQueue = new ArrayDeque<>();
        travelQueue.add(node);
        while (!travelQueue.isEmpty()) {
            T visitedNode = travelQueue.remove();
            nodeList.add(visitedNode);
            if (!visitedNode.getChildren().isEmpty())
                travelQueue.addAll((Collection<? extends T>) visitedNode.getChildren());
        }
        return nodeList;
    }

    @Override
    public T search(G value) {
        return search(node -> node.getValue().equals(value));
    }

    @Override
    public T search(Predicate<T> predicate) {
        List<T> nodeList = breadthFirstTravel(rootNode);
        Optional<T> searchedOptional = nodeList.stream().filter(predicate::test).findFirst();
        return searchedOptional.orElse(null);
    }

    @Override
    public Stream<T> stream() throws TreeOperationException {
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

    @Override
    public <U, I> Tree<U, I> convert(Function<T, U> function) throws TreeOperationException {
        U apply = function.apply(rootNode);
        Assert.isAssignable(Node.class, apply.getClass());
        Node<I> convertedRootNode = (Node<I>) apply;
        Tree<Node<I>, I> simpleTree = new SimpleTree<>(convertedRootNode);
        processChildren(rootNode, simpleTree, function, convertedRootNode);
        return (Tree<U, I>) simpleTree;
    }

    private <I, U> void processChildren(T originalNode, Tree<Node<I>, I> simpleTree, Function<T, U> function, Node<I> convertedParentNode) throws TreeOperationException {
        List<T> children = (List<T>) originalNode.getChildren();
        for (T childNode : children) {
            Node<I> convertedChildNode = (Node<I>) function.apply(childNode);
            simpleTree.addNode(convertedParentNode, convertedChildNode);
            processChildren(childNode, simpleTree, function, convertedChildNode);
        }
    }

    private boolean isLeafNode(T node) {
        return node.getChildren().isEmpty();
    }
}
