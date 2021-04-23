package com.markdownsite.core;

import com.markdownsite.core.interfaces.ResourceProvider;
import com.markdownsite.integration.interfaces.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Component
public class DefaultResourceProvider implements ResourceProvider {

    @Autowired
    private List<ResourceConfig> resourceConfigList;

    @Override
    public Set<String> getJsResources() {
        Set<String> resources = getResources(ResourceConfig::getJsResources);
        resources.add("https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js");
        return resources;
    }

    @Override
    public Set<String> getCssResources() {
        Set<String> resources = getResources(ResourceConfig::getCssResources);
        resources.add("https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css");
        resources.add("https://fonts.googleapis.com/icon?family=Material+Icons");
        return resources;
    }

    @Override
    public Set<String> getInlineJsResources() {
      return getResources(ResourceConfig::getInlineJsResources);
    }

    @Override
    public Set<String> getInlineCssResources() {
        return getResources(ResourceConfig::getInlineCssResources);
    }

    private Set<String> getResources(Function<ResourceConfig, List<String>> extractResource) {
        Set<String> jsResources = new HashSet<>();

        if (!resourceConfigList.isEmpty())
            resourceConfigList.forEach(resourceConfig -> {
                List<String> configJsResources = extractResource.apply(resourceConfig);
                if (configJsResources != null && !configJsResources.isEmpty())
                    jsResources.addAll(configJsResources);
            });
        return jsResources;
    }
}
