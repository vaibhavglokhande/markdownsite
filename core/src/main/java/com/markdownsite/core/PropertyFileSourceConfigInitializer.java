package com.markdownsite.core;

import com.markdownsite.core.interfaces.SourceConfigInitializer;
import com.markdownsite.integration.models.SourceProviderConfigProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties
@Component("PropertyFileSourceConfigInitializer")
public class PropertyFileSourceConfigInitializer implements SourceConfigInitializer {

    private Environment environment;

    @Autowired
    public PropertyFileSourceConfigInitializer(Environment environment) {
        this.environment = environment;
    }

    @Override
    public List<SourceProviderConfigProperty> initProperties(List<SourceProviderConfigProperty> sourceProviderConfig) {
        if(sourceProviderConfig == null)
            return null;
        List<SourceProviderConfigProperty> updatedProperties = new ArrayList<>();
        for (SourceProviderConfigProperty sourceProviderConfigProperty : sourceProviderConfig) {
            // Make copy of properties before update.
            SourceProviderConfigProperty copiedProperties = new SourceProviderConfigProperty(
                    sourceProviderConfigProperty.getPropertyName(),
                    sourceProviderConfigProperty.getPropertyValue(),
                    sourceProviderConfigProperty.getChildrenProperties(),
                    sourceProviderConfigProperty.getConfigurablePropertiesRules());
            String propertyName = copiedProperties.getPropertyName();
            Object property = environment.getProperty(propertyName, sourceProviderConfigProperty.getPropertyValue().getClass());
            if(property != null)
                copiedProperties.setPropertyValue(property);
            updatedProperties.add(copiedProperties);
        }
        return updatedProperties;
    }
}
