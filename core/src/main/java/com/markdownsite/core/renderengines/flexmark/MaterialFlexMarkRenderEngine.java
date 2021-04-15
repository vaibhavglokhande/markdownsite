package com.markdownsite.core.renderengines.flexmark;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component("MaterialFlexMarkRenderEngine")
public class MaterialFlexMarkRenderEngine extends AbstractFlexMarkRenderEngine {
    private UUID uuid = UUID.nameUUIDFromBytes(this.getClass().getName().getBytes(StandardCharsets.UTF_8));

    @Override
    public boolean supports(String uuid) {
        return this.uuid.toString().equals(uuid);
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public List<String> getJsResources() {
        List<String> jsResources = new ArrayList<>();
        List<String> defaultJsResources = super.getJsResources();
        if(defaultJsResources != null && !defaultJsResources.isEmpty())
            jsResources.addAll(defaultJsResources);
        jsResources.add("https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js");
        return jsResources;
    }

    @Override
    public List<String> getCssResources() {
        List<String> cssResources = new ArrayList<>();
        List<String> defaultCssResources = super.getCssResources();
        if(defaultCssResources != null && !defaultCssResources.isEmpty())
            cssResources.addAll(defaultCssResources);
        cssResources.add("https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css");
        return cssResources;
    }
}
