package com.markdownsite.integration.models;

import lombok.Data;

/**
 * The type Source navigation node.
 *
 */
@Data
public class SourceNavigationNode extends Node<String>{
    /**
     * The Is directory.
     */
    boolean directory;
    private String displayName;
}
