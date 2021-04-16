package com.markdownsite.core;

import com.markdownsite.integration.interfaces.MarkdownSource;
import com.markdownsite.integration.models.MarkdownElement;
import com.markdownsite.integration.models.SourceProviderConfig;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Component
public class FileBasedMarkdown implements MarkdownSource<String> {

    private SourceProviderConfig sourceProviderConfig = new SourceProviderConfig(new ArrayList<>());

    public static final String IDENTIFIER = FileBasedMarkdown.class.getName();

    @Override
    public MarkdownElement<String> getMarkdownElement(String identifier) {
        return null;
    }

    @Override
    public Map<String, MarkdownElement<String>> getAll() {
        return null;
    }

    @Override
    public SourceProviderConfig getSourceConfig() {
        return sourceProviderConfig;
    }

    @Override
    public void updateSourceConfig(SourceProviderConfig sourceProviderConfig) {
        this.sourceProviderConfig = sourceProviderConfig;
    }

    @Override
    public String sourceIdentifier() {
        return IDENTIFIER;
    }
}
