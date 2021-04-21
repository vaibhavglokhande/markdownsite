package com.markdownsite.integration.interfaces;

import com.markdownsite.integration.models.SourceNavigationNode;

/**
 *
 * {@inheritDoc}
 * The interface Navigable markdown source.
 * The navigable markdown source provides the markdown sources which can be navigated.
 * It provides a hierarchical structure to navigate through different {@link com.markdownsite.integration.models.MarkdownElement}
 *
 */
public interface NavigableMarkdownSource<T> extends MarkdownSource<T> {
    /**
     * Provides the tree to build the navigation tree.
     *
     * @return the navigation tree
     */
    Tree<SourceNavigationNode, String> getNavigationTree();
}
