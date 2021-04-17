package com.markdownsite.integration.interfaces;

/**
 * The interface Navigable markdown source.
 * The navigable markdown source provides the markdown sources which can be navigated.
 * It provides a hierarchical structure to navigate through different {@link com.markdownsite.integration.models.MarkdownElement}
 *
 * @param <T> the type parameter
 */
public interface NavigableMarkdownSource<T> extends MarkdownSource<T> {
}
