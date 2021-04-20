package com.markdownsite.web.controller;

import com.markdownsite.core.FileBasedMarkdownSource;
import com.markdownsite.core.RenderEngineFactory;
import com.markdownsite.core.interfaces.ResourceProvider;
import com.markdownsite.integration.exceptions.AbstractException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import com.markdownsite.integration.interfaces.RenderEngine;
import com.markdownsite.integration.models.MarkdownElement;
import com.markdownsite.integration.models.RenderEngineConfigProperty;
import com.markdownsite.integration.models.SourceProviderConfigProperty;
import com.markdownsite.integration.providers.MarkdownSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class UIController {

    // Hardcoding this value for time being.
    // TODO Remove this hardcoded value, use configuration.
    private String renderEngineUUID = "9971ea80-dec5-3154-af52-771b08068b97";

    private RenderEngineFactory renderEngineFactory;
    private ResourceProvider resourceProvider;
    private MarkdownSourceProvider markdownSourceProvider;


    @Autowired
    public UIController(RenderEngineFactory renderEngineFactory, ResourceProvider resourceProvider, MarkdownSourceProvider markdownSourceProvider) {
        this.renderEngineFactory = renderEngineFactory;
        this.resourceProvider = resourceProvider;
        this.markdownSourceProvider = markdownSourceProvider;
    }

    @GetMapping(path = "/docs/{*docId}")
    public String renderDoc(@PathVariable String docId, Model model) throws AbstractException {
        RenderEngine renderEngine = renderEngineFactory.getRenderEngine(renderEngineUUID);
        MarkdownSource<FileBasedMarkdownSource, String> source = (MarkdownSource<FileBasedMarkdownSource, String>) markdownSourceProvider.getSource(FileBasedMarkdownSource.IDENTIFIER);
        ConcurrentHashMap<String, SourceProviderConfigProperty<String>> sourceProviderConfig = new ConcurrentHashMap<>();
        Path path = Paths.get("core", "src", "test", "resources", "markdown-files");
        SourceProviderConfigProperty<String> configProperty = new SourceProviderConfigProperty<>(FileBasedMarkdownSource.PROPERTY_SOURCE_DIR, path.toString());
        sourceProviderConfig.put(FileBasedMarkdownSource.PROPERTY_SOURCE_DIR, configProperty);
        source.updateSourceConfig(sourceProviderConfig);
        source.initializeSource();
        Map<String, MarkdownElement<FileBasedMarkdownSource>> allSources = source.getAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, MarkdownElement<FileBasedMarkdownSource>> entry : allSources.entrySet()) {
            MarkdownElement<FileBasedMarkdownSource> fileBasedMarkdownSourceMarkdownElement = entry.getValue();
            stringBuilder.append(renderEngine.render(fileBasedMarkdownSourceMarkdownElement.getContent()))
                    .append("\n");
        }
        model.addAttribute("defaultCSS", resourceProvider.getCssResources());
        model.addAttribute("defaultScript", resourceProvider.getJsResources());
        model.addAttribute("inlineCSS", resourceProvider.getInlineCssResources());
        model.addAttribute("inlineScript", resourceProvider.getInlineJsResources());
        model.addAttribute("markdownRender", stringBuilder.toString());
        return "docs/markdown-render";
    }

}
