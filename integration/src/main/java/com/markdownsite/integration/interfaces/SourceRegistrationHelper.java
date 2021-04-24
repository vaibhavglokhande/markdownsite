package com.markdownsite.integration.interfaces;

import com.markdownsite.integration.exceptions.PropertyValidationException;
import com.markdownsite.integration.models.SourceInfo;
import com.markdownsite.integration.models.SourceProviderConfigProperty;

import java.util.List;

/**
 * The interface Source registration helper.
 * The implementation of this interface provides api to build the MarkdownSource object which can be registered at runtime.
 */
public interface SourceRegistrationHelper {

    /**
     * Supports boolean.
     * Whether a particular type of source is supported or not.
     *
     * @param sourceIdentifier the source identifier
     * @return the boolean true if supported, false otherwise.
     */
    boolean supports(String sourceIdentifier);

    /**
     * Gets default config properties.
     *
     * @return the default config properties
     */
    List<SourceProviderConfigProperty> getDefaultConfigProperties();

    /**
     * Build source markdown source.
     *
     * @param sourceInfo       the source info
     * @param configProperties the config properties
     * @return the markdown source
     */
    MarkdownSource buildSource(SourceInfo sourceInfo, List<SourceProviderConfigProperty> configProperties) throws PropertyValidationException;

    /**
     * Gets source template info, the identifier of the source, display name of source and description.
     *
     * @return the source template info
     */
    SourceInfo getSourceTemplateInfo();
}
