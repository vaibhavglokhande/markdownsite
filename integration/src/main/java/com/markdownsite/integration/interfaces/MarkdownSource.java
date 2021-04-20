package com.markdownsite.integration.interfaces;

import com.markdownsite.integration.exceptions.AbstractException;
import com.markdownsite.integration.exceptions.PropertyValidationException;
import com.markdownsite.integration.models.MarkdownElement;
import com.markdownsite.integration.models.SourceProviderConfigProperty;

import java.util.Map;

/**
 * The interface Markdown source.
 * This interface should be implemented by the service that provides the markdown content source.
 * Each markdown element is identified by unique identifier
 *
 * @param <T> the type parameter for the source content type
 * @param <G> the type parameter for the source identifier type
 */
public interface MarkdownSource<T, G> {

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
    MarkdownElement<T> getMarkdownElement(T identifier);

    /**
     * Provide the map of all the markdown source elements identified by their unique identifier.
     *
     * @return the all
     */
    Map<G, MarkdownElement<T>> getAll();

    /**
     * Returns the configuration for the source.
     *
     * @param <G> the type parameter
     * @return the source config
     */
     Map<String, SourceProviderConfigProperty<G>> getSourceConfig();

    /**
     * Update source config.
     *
     * @param sourceProviderConfig the source provider config
     * @throws PropertyValidationException the property validation exception
     */
    void updateSourceConfig(Map<String, SourceProviderConfigProperty<G>> sourceProviderConfig) throws PropertyValidationException;

    /**
     * Source identifier string.
     * This string can be any string that can identify the provider uniquely.
     *
     * <p>This recommended value is the complete class name</p>
     *
     * @return the string
     */
    String sourceIdentifier();

}
