package com.markdownsite.core.interfaces;

import com.markdownsite.core.exceptions.UIServiceException;
import com.markdownsite.integration.exceptions.AbstractException;
import com.markdownsite.integration.exceptions.SourceException;
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
     * @param sourceIdentifier the source identifier
     * @return the navigation tree
     * @throws AbstractException the abstract exception
     */
    Tree<SourceNavigationNode, String> getNavigationTree(String sourceIdentifier) throws AbstractException;

    /**
     * Gets content.
     * The content is the html rendered by the render engine provided by the source.
     * The source is selected based on the identifier provided.
     *
     * @param sourceIdentifier the source identifier to select the source
     * @param identifier       the identifier of the source element to render content from
     * @return the content - the html content rendered by the render engine
     * @throws AbstractException the abstract exception
     */
    String getContent(String sourceIdentifier, String identifier) throws AbstractException;

    /**
     * Gets or default content.
     * The content is the html rendered by the render engine provided by the source.
     * THe source is selected based on the identifier provided.
     *
     * @param sourceIdentifier the source identifier to select the source
     * @param identifier       the identifier of the source element to render content from
     * @return the or default content - the html content rendered by the render engine or the first file found if no valid identifier is provided.
     * @throws UIServiceException the ui service exception
     * @throws SourceException    the source exception
     */
    String getOrDefaultContent(String sourceIdentifier, String identifier) throws UIServiceException, SourceException;
}
