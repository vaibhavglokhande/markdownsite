package com.markdownsite.core.interfaces;

import com.markdownsite.core.exceptions.SourceRegistrationException;
import com.markdownsite.integration.models.SourceInfo;
import com.markdownsite.integration.models.SourceProviderConfigProperty;

import java.util.List;

/**
 * The interface Source registration service.
 * The implementation of this service provides the services to register the sources available for configuration.
 */
public interface SourceRegistrationService {

    /**
     * Gets registrable sources.
     * Provides the list of sources which can be registered.
     *
     * @return the registrable sources
     */
    List<SourceInfo> getRegistrableSources() throws SourceRegistrationException;

    /**
     * Register source.
     * Register new source, configure and initializes it.
     *
     * @param sourceInfo       the source info
     * @param configProperties the config properties
     * @throws SourceRegistrationException the source registration exception if the source is not configured, initialized or failed to register.
     */
    void registerSource(SourceInfo sourceInfo, List<SourceProviderConfigProperty> configProperties) throws SourceRegistrationException;

    /**
     * Gets source config.
     * Provides the default configuration fields required to initialize the source.
     *
     * @param sourceTypeIdentifier the source type identifier
     * @return the source config
     */
    List<SourceProviderConfigProperty> getSourceConfig(String sourceTypeIdentifier) throws SourceRegistrationException;

}
