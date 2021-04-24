package com.markdownsite.integration.providers;

import com.markdownsite.integration.enums.SourceErrorCode;
import com.markdownsite.integration.exceptions.SourceException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class MarkdownSourceProvider {

    private Map<String, MarkdownSource> markdownSources;

    public MarkdownSourceProvider(Map<String, MarkdownSource> markdownSources) {
        this.markdownSources = markdownSources;
    }

    public MarkdownSource getSource(String sourceName) throws SourceException {
        if (markdownSources == null)
            throw new SourceException(SourceErrorCode.SOURCE_NOT_CONFIGURED_EXCEPTION);
        Optional<MarkdownSource> source = Optional.ofNullable(markdownSources.get(processKey(sourceName)));
        return source.orElseThrow(() -> new SourceException(SourceErrorCode.SOURCE_NOT_FOUND_EXCEPTION));
    }

    public Map<String, MarkdownSource> getAllSources() throws SourceException {
        if (markdownSources == null)
            throw new SourceException(SourceErrorCode.SOURCE_NOT_CONFIGURED_EXCEPTION);
        return markdownSources;
    }

    public void registerSource(MarkdownSource markdownSource) throws SourceException {
        if (markdownSources.containsKey(processKey(markdownSource.sourceName())))
            throw new SourceException(SourceErrorCode.SOURCE_ALREADY_CONFIGURED);
        markdownSources.put(processKey(markdownSource.sourceName()), markdownSource);
    }

    public void removeSource(String sourceName) throws SourceException {
        if(!markdownSources.containsKey(processKey(sourceName)))
            throw new SourceException(SourceErrorCode.SOURCE_NOT_FOUND_EXCEPTION);
        markdownSources.remove(processKey(sourceName));
    }

    private String processKey(String key) {
        return key.toLowerCase();
    }

    public MarkdownSource getConfiguredSource() throws SourceException {
        return getSource("HelpDocs");
    }

}
