package com.markdownsite.integration.providers;

import com.markdownsite.integration.exceptions.SourceNotFoundException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class MarkdownSourceProvider {

    @Autowired
    private Set<MarkdownSource<?>> markdownSources;

    public MarkdownSource<?> getSource(String sourceIdentifier) throws SourceNotFoundException {
        if (markdownSources == null)
            throw new SourceNotFoundException(com.markdownsite.integration.enums.SourceNotFoundException.SOURCE_NOT_CONFIGURED_EXCEPTION);
        Optional<MarkdownSource<?>> source = markdownSources.stream().filter(markdownSource -> markdownSource.sourceIdentifier().equalsIgnoreCase(sourceIdentifier)).findFirst();
        return source.orElseThrow(() -> new SourceNotFoundException(com.markdownsite.integration.enums.SourceNotFoundException.SOURCE_NOT_FOUND_EXCEPTION));
    }

}
