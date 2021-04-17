package com.markdownsite.integration.interfaces;

import java.util.List;

/**
 * The interface Navigable tree.
 * The implementation provides a tree data structure with navigation between nodes.
 *
 * @param <T> the type parameter
 * @param <G> the type parameter
 */
public interface NavigableTree<T, G> {

    /**
     * Gets the parent of the current node.
     *
     * @param node the node
     * @return the parent node if present, null if no parent is present.
     */
    T getParent(T node);

    /**
     * Gets children of the current node.
     *
     * @param node the node
     * @return the children nodes, empty list if none are present.
     */
    List<T> getChildren(T node);

    /**
     * Add node to the tree.
     * The leaf node is attached to the given parent node and vice versa.
     *
     * @param parentNode the parent node to which child node is attached.
     * @param childNode  the child node to be added to the tree.
     * @return the the new child node attached to the tree.
     */
    T addNode(T parentNode, T childNode);

    /**
     * Add nodes to the root node as immediate leaf nodes.
     *
     * @param parentNode    the parent node to which nodes are to be attached.
     * @param childrenNodes the children nodes to be added to tree.
     * @return the t
     */
    List<T> addNodes(T parentNode, List<T> childrenNodes);

    /**
     * Traverse the data structure and provide the list of the nodes including the root node.
     *
     * @param node         the node to begin search from.
     * @param simpleTraverseMode the traverse mode specifies the mode of the traverse direction.
     * @return the list of traversed nodes.
     */
    List<T> traverse(T node, TreeTraverseMode simpleTraverseMode);

    /**
     * Search the node based on the passed value.
     *
     * @param value the value to be searched
     * @return the Node.
     */
    T search(G value);

}
