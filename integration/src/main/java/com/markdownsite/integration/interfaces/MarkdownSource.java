package com.markdownsite.integration.interfaces;

import com.markdownsite.integration.exceptions.AbstractException;
import com.markdownsite.integration.exceptions.PropertyValidationException;
import com.markdownsite.integration.models.MarkdownElement;
import com.markdownsite.integration.models.SourceProviderConfigProperty;

import java.util.List;
import java.util.Map;

/**
 * The interface Markdown source.
 * This interface should be implemented by the service that provides the markdown content source.
 * Each markdown element is identified by unique identifier
 *
 */
public interface MarkdownSource {

    /**
     * Initialize the source.
     * This method is called before using the source to render the markdown.
     *
     * @throws AbstractException the abstract exception
     */
    void initializeSource() throws AbstractException;

    /**
     * Gets markdown source element based on the passed identifier.
     *
     * @param identifier the identifier
     * @return the markdown source
     */
    MarkdownElement<String> getMarkdownElement(String identifier);

    /**
     * Provide the map of all the markdown source elements identified by their unique identifier.
     *
     * @return the all
     */
    Map<String, MarkdownElement<String>> getAll();

    /**
     * Returns the configuration for the source.
     * Provide the empty implementation of all the required config properties by default.
     *
     * @return the source config
     */
     List<SourceProviderConfigProperty> getSourceConfig();

    /**
     * Update source config.
     *
     * @param sourceProviderConfig the source provider config
     * @throws PropertyValidationException the property validation exception
     */
    void updateSourceConfig(List<SourceProviderConfigProperty> sourceProviderConfig) throws PropertyValidationException;

    /**
     * Source identifier string.
     * This string can be any string that can identify the provider uniquely.
     *
     * <p>This recommended value is the complete class name</p>
     *
     * @return the string
     */
    String sourceIdentifier();

    String sourceName();

    String sourceDescription();

}
