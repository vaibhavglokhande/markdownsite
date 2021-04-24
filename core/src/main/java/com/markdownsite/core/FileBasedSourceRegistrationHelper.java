package com.markdownsite.core;

import com.markdownsite.integration.exceptions.PropertyValidationException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import com.markdownsite.integration.interfaces.SourceRegistrationHelper;
import com.markdownsite.integration.models.SourceInfo;
import com.markdownsite.integration.models.SourceProviderConfigProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileBasedSourceRegistrationHelper implements SourceRegistrationHelper {

    @Override
    public boolean supports(String sourceIdentifier) {
        FileBasedMarkdownSource templateSource = getTemplateSource();
        return templateSource.sourceIdentifier().equals(sourceIdentifier);
    }

    @Override
    public List<SourceProviderConfigProperty> getDefaultConfigProperties() {
        FileBasedMarkdownSource templateSource = getTemplateSource();
        return templateSource.getSourceConfig();
    }

    @Override
    public MarkdownSource buildSource(SourceInfo sourceInfo, List<SourceProviderConfigProperty> configProperties) throws PropertyValidationException {
        FileBasedMarkdownSource fileBasedMarkdownSource = new FileBasedMarkdownSource(sourceInfo.getSourceName(), sourceInfo.getSourceDescription());
        fileBasedMarkdownSource.updateSourceConfig(configProperties);
        return fileBasedMarkdownSource;
    }

    @Override
    public SourceInfo getSourceTemplateInfo() {
        FileBasedMarkdownSource templateSource = getTemplateSource();
        SourceInfo sourceInfo = new SourceInfo();
        sourceInfo.setSourceId(templateSource.sourceIdentifier());
        sourceInfo.setSourceName("File based source");
        sourceInfo.setSourceDescription("This source uses markdown files' source directory to build the content and render as site.");
        return sourceInfo;
    }

    private FileBasedMarkdownSource getTemplateSource() {
        return new FileBasedMarkdownSource("template-source");
    }
}
