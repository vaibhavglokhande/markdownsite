package com.markdownsite.core.interfaces;

import com.markdownsite.integration.models.SourceProviderConfigProperty;

import java.util.List;

/**
 * The type Source config initializer.
 * The implementation of this interface initializes the provided properties with the default values provided by the property source.
 */
public interface SourceConfigInitializer {

    /**
     * Init properties list.
     * This method configures the required properties from the property source and updates the corresponding value.
     * If no property is found, the value remains unchanged.
     *
     * @param sourceProviderConfig the source provider config
     * @return the list after the properties have initialized
     */
    List<SourceProviderConfigProperty> initProperties(List<SourceProviderConfigProperty> sourceProviderConfig);
}
