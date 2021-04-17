package com.markdownsite.integration.models;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class SourceProviderConfigProperty<T>  {
    private String propertyName;
    private T propertyValue;
    private List<SourceProviderConfig> childrenProperties;
    private ConfigurablePropertiesRules<T> configurablePropertiesRules;

    public SourceProviderConfigProperty(String propertyName, T propertyValue) {
        this(propertyName, propertyValue, new ArrayList<>());
    }

    public SourceProviderConfigProperty(String propertyName, T propertyValue, List<SourceProviderConfig> childrenProperties) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
        this.childrenProperties = childrenProperties;
    }

    public SourceProviderConfigProperty(String propertyName, T propertyValue, List<SourceProviderConfig> childrenProperties, ConfigurablePropertiesRules<T> configurablePropertiesRules) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
        this.childrenProperties = childrenProperties;
        this.configurablePropertiesRules = configurablePropertiesRules;
    }
}
