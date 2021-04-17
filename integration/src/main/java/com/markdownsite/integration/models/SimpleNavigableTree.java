package com.markdownsite.integration.models;

import com.google.gson.Gson;
import com.markdownsite.integration.interfaces.NavigableTree;
import com.markdownsite.integration.interfaces.TreeTraverseMode;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * SimpleNavigableTree provides a simple data structure with hierarchical relationship of nodes.
 * <p>The tree is located at single root node and each node can have multiple children nodes. </p>
 */
public class SimpleNavigableTree<T extends Node<G>, G> implements NavigableTree<T, G> {

    @Getter
    private T rootNode;

    public SimpleNavigableTree(T rootNode) {
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
     * <p>The child node is not updated, where as the parent node is updated and the new child node is attached to it.</p>
     * */
    @Override
    public T addNode(T parentNode, T childNode) {
        Assert.notNull(parentNode, "Parent node is null.");
        Assert.notNull(childNode, "Child node is null.");
        Gson gson = new Gson();
        synchronized (this) {
            // Make a deep copy of the object
            T newChildNode = (T) gson.fromJson(gson.toJson(childNode), childNode.getClass());
            // Set parent-child relationship.
            newChildNode.setParent(parentNode);
            // Add the node to the tree.
            parentNode.addChild(newChildNode);
            return newChildNode;
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
}
