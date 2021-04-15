package com.markdownsite.integration.models;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class RenderEngineConfigProperty<T> {
    private String propertyName;
    private T propertyValue;
    private List<RenderEngineConfigProperty> childrenProperties;
    private boolean enabled;

    public RenderEngineConfigProperty(String propertyName, T propertyValue, boolean enabled) {
        this(propertyName, propertyValue, new ArrayList<>(), enabled);
    }

    public RenderEngineConfigProperty(String propertyName, T propertyValue, List<RenderEngineConfigProperty> childrenProperties, boolean enabled) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
        this.childrenProperties = childrenProperties;
        this.enabled = enabled;
    }
}
