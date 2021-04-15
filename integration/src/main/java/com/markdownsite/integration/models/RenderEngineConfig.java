package com.markdownsite.integration.models;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class RenderEngineConfig {
    private Map<String, RenderEngineConfigProperty> configPropertyMap = new ConcurrentHashMap<>();
}
