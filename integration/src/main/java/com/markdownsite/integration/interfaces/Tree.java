package com.markdownsite.integration.interfaces;

import com.markdownsite.integration.exceptions.TreeOperationException;
import com.markdownsite.integration.models.Node;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * The interface Navigable tree.
 * The implementation provides a tree data structure with navigation between nodes.
 *
 * @param <T> the type parameter for node type
 * @param <G> the type parameter for node content type
 */
public interface Tree<T, G> {


    /**
     * Gets root node.
     *
     * @return the root node
     */
    T getRootNode();

    /**
     * Gets the parent of the current node.
     *
     * @param node the node
     * @return the parent node if present, null if no parent is present.
     * @throws TreeOperationException the tree operation exception
     */
    T getParent(T node) throws TreeOperationException;

    /**
     * Gets children of the current node.
     *
     * @param node the node
     * @return the children nodes, empty list if none are present.
     * @throws TreeOperationException the tree operation exception
     */
    List<T> getChildren(T node) throws TreeOperationException;

    /**
     * Add node to the tree.
     * The leaf node is attached to the given parent node and vice versa.
     *
     * @param parentNode the parent node to which child node is attached.
     * @param childNode  the child node to be added to the tree.
     * @return the the new child node attached to the tree.
     * @throws TreeOperationException the tree operation exception
     */
    T addNode(T parentNode, T childNode) throws TreeOperationException;

    /**
     * Add nodes to the root node as immediate leaf nodes.
     *
     * @param parentNode    the parent node to which nodes are to be attached.
     * @param childrenNodes the children nodes to be added to tree.
     * @return the t
     * @throws TreeOperationException the tree operation exception
     */
    List<T> addNodes(T parentNode, List<T> childrenNodes) throws TreeOperationException;

    /**
     * Traverse the data structure and provide the list of the nodes including the root node.
     *
     * @param node               the node to begin search from.
     * @param simpleTraverseMode the traverse mode specifies the mode of the traverse direction.
     * @return the list of traversed nodes.
     * @throws TreeOperationException the tree operation exception
     */
    List<T> traverse(T node, TreeTraverseMode simpleTraverseMode) throws TreeOperationException;

    /**
     * Search the node based on the passed value.
     *
     * @param value the value to be searched
     * @return the Node, null if not present.
     * @throws TreeOperationException the tree operation exception
     */
    T search(G value) throws TreeOperationException;

    /**
     * Search the node based on the predicate provided by the consumer.
     *
     * @param predicate the predicate
     * @return the t
     * @throws TreeOperationException the tree operation exception
     */
    T search(Predicate<T> predicate);

    /**
     * Provides the stream for the data structure
     *
     * @return the stream
     * @throws TreeOperationException the tree operation exception
     */
    Stream<T> stream() throws TreeOperationException;

    /**
     * Delete the given node from the tree.
     * <p>Node deletion strategy depends on specific tree implementation.</p>
     *
     * @param node the node
     * @throws TreeOperationException the tree operation exception
     */
    void delete(T node) throws TreeOperationException;

    /**
     * Convert tree.
     *
     * @param <U>      the type parameter for the Node to convert to.
     * @param <I>      the type parameter for the value type of the node.
     * @param function the function
     * @return the tree
     * @throws TreeOperationException the tree operation exception
     */
    <U, I> Tree<U, I> convert(Function<T, U> function) throws TreeOperationException;

}
