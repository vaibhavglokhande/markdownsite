package com.markdownsite.integration.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(of = {"propertyName"})
public class SourceProviderConfigProperty<T> {
    private String propertyName;
    @NonNull
    private T propertyValue;
    private List<SourceProviderConfigProperty> childrenProperties;
    private ConfigurablePropertiesRules<T> configurablePropertiesRules;

    // TODO Remove this constructor. For UI, add it's separate model and map it to this.
    public SourceProviderConfigProperty() {
    }

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
