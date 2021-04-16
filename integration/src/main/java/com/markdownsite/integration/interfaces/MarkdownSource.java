package com.markdownsite.integration.interfaces;

import com.markdownsite.integration.models.MarkdownElement;
import com.markdownsite.integration.models.SourceProviderConfig;

import java.util.Map;

/**
 * The interface Markdown source.
 * This interface should be implemented by the service that provides the markdown content source.
 * Each markdown element is identified by unique identifier
 *
 * @param <T> the type parameter
 */
public interface MarkdownSource<T> {

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
    Map<T, MarkdownElement<T>> getAll();

    /**
     * Returns the configuration for the source.
     *
     * @return the source config
     */
    SourceProviderConfig getSourceConfig();

    /**
     * Update source config.
     *
     * @param sourceProviderConfig the source provider config
     */
    void updateSourceConfig(SourceProviderConfig sourceProviderConfig);

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
