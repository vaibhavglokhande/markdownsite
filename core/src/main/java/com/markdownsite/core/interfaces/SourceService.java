package com.markdownsite.core.interfaces;

import com.markdownsite.integration.exceptions.SourceException;
import com.markdownsite.integration.models.SourceInfo;
import com.markdownsite.integration.models.SourceProviderConfigProperty;

import java.util.List;

/**
 * The interface Source service.
 * The implementation of this interface provides services related to markdown sources.
 */
public interface SourceService {

    /**
     * Gets all sources info that are configured.
     *
     * @return the all sources info
     * @throws SourceException the source exception
     */
    List<SourceInfo> getAllSourcesInfo() throws SourceException;

    /**
     * Gets source info.
     *
     * @param sourceId the source id
     * @return the source info
     * @throws SourceException the source exception
     */
    SourceInfo getSourceInfo(String sourceId) throws SourceException;

}
