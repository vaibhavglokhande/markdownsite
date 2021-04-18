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
    private List<SourceProviderConfigProperty> childrenProperties;
    private ConfigurablePropertiesRules<T> configurablePropertiesRules;

    public SourceProviderConfigProperty(String propertyName, T propertyValue) {
        this(propertyName, propertyValue, new ArrayList<>());
    }

    public SourceProviderConfigProperty(String propertyName, T propertyValue, List<SourceProviderConfigProperty> childrenProperties) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
        this.childrenProperties = childrenProperties;
    }

    public SourceProviderConfigProperty(String propertyName, T propertyValue, List<SourceProviderConfigProperty> childrenProperties, ConfigurablePropertiesRules<T> configurablePropertiesRules) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
        this.childrenProperties = childrenProperties;
        this.configurablePropertiesRules = configurablePropertiesRules;
    }
}
