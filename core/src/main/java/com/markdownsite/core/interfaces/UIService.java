package com.markdownsite.core.interfaces;

import com.markdownsite.integration.exceptions.AbstractException;
import com.markdownsite.integration.interfaces.Tree;
import com.markdownsite.integration.models.SourceNavigationNode;

/**
 * The interface Ui service.
 * The UIService provides the apis to build the required UI interface to access
 * and navigate the markdown.
 */
public interface UIService {

    /**
     * Gets navigation tree.
     * The navigation tree uses {@see SourceNavigationNode} to provide the
     * navigation node. The value of the node is the identifier of the source,
     * and other fields as required.
     *
     * @return the navigation tree
     */
    Tree<SourceNavigationNode, String> getNavigationTree() throws AbstractException;

    /**
     * Gets content.
     * The content is the html rendered by the render engine provided by the source.
     * The source is selected based on the identifier provided.
     *
     * @param identifier the identifier of the source
     * @return the content - the html content rendered by the render engine
     */
    String getContent(String identifier) throws AbstractException;
}
