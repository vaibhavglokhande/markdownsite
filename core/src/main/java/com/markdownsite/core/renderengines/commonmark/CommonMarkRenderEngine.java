package com.markdownsite.core.renderengines.commonmark;

import com.markdownsite.integration.interfaces.RenderEngine;
import com.markdownsite.integration.models.RenderEngineConfigProperty;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

@Component("CommonMarkRenderEngine")
public class CommonMarkRenderEngine implements RenderEngine {


    private UUID uuid = UUID.nameUUIDFromBytes(this.getClass().getName().getBytes(StandardCharsets.UTF_8));

    @Override
    public String render(String markdownContent) {
       throw new UnsupportedOperationException("Method not defined.");
    }

    @Override
    public String render(File markdownFile) {
        throw new UnsupportedOperationException("Method not defined.");
    }

    @Override
    public String engineName() {
        return "CommonMarkEngine";
    }

    @Override
    public String engineDescription() {
        return """
                This engine is based on Commonmark-java project.
                Please refer to the link for more information - https://github.com/commonmark/commonmark-java
                """;
    }

    @Override
    public Map<String, RenderEngineConfigProperty> getConfig() {
        return null;
    }

    @Override
    public void updateRenderEngineConfig(Map<String, RenderEngineConfigProperty> renderEngineConfig) {
        throw new UnsupportedOperationException("Method not defined.");
    }

    @Override
    public boolean supports(String uuid) {
        return this.uuid.toString().equals(uuid);
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }
}
