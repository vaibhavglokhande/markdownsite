package com.markdownsite.integration.models;

import lombok.Data;

/**
 * The type Source navigation node.
 *
 * @param <T> the type parameter for type of MarkdownElement
 */
@Data
public class SourceNavigationNode<T> extends Node<String>{
    /**
     * The Markdown element.
     */
    private MarkdownElement<T> markdownElement;
    /**
     * The Is directory.
     */
    boolean isDirectory;
    private String displayName;
}
