package com.markdownsite.integration.interfaces;

import com.markdownsite.integration.models.MarkdownElement;
import com.markdownsite.integration.models.SourceNavigationNode;

/**
 * The interface Navigable markdown source.
 * The navigable markdown source provides the markdown sources which can be navigated.
 * It provides a hierarchical structure to navigate through different {@link com.markdownsite.integration.models.MarkdownElement}
 *
 * @param <T> the type parameter for the source content type
 * @param <G> the type parameter for the source identifier type
 */
public interface NavigableMarkdownSource<T, G> extends MarkdownSource<T, G> {
    /**
     * Provides the tree to build the navigation tree.
     *
     * @return the navigation tree
     */
    Tree<SourceNavigationNode<T>, String> getNavigationTree();
}
