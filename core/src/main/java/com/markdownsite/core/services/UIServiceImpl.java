package com.markdownsite.core.services;

import com.markdownsite.core.RenderEngineFactory;
import com.markdownsite.core.enums.UIServiceErrorCode;
import com.markdownsite.core.exceptions.UIServiceException;
import com.markdownsite.core.interfaces.UIService;
import com.markdownsite.integration.exceptions.SourceException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import com.markdownsite.integration.interfaces.NavigableMarkdownSource;
import com.markdownsite.integration.interfaces.RenderEngine;
import com.markdownsite.integration.interfaces.Tree;
import com.markdownsite.integration.models.MarkdownElement;
import com.markdownsite.integration.models.SourceNavigationNode;
import com.markdownsite.integration.providers.MarkdownSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

@Service
public class UIServiceImpl implements UIService {

    public static final String NOT_FOUND = """
            # 404
                            
            ## Not found
            """;
    private MarkdownSourceProvider sourceProvider;
    private RenderEngineFactory renderEngineFactory;

    @Autowired
    public UIServiceImpl(MarkdownSourceProvider sourceProvider, RenderEngineFactory renderEngineFactory) {
        this.sourceProvider = sourceProvider;
        this.renderEngineFactory = renderEngineFactory;
    }

    @Override
    public Tree<SourceNavigationNode, String> getNavigationTree(String sourceIdentifier) throws SourceException, UIServiceException {
        NavigableMarkdownSource markdownSource = getNavigableMarkdownSource(sourceIdentifier);
        return markdownSource.getNavigationTree();
    }

    private NavigableMarkdownSource getNavigableMarkdownSource(String sourceIdentifier) throws SourceException, UIServiceException {
        MarkdownSource configuredSource = sourceProvider.getSource(sourceIdentifier);
        if (!NavigableMarkdownSource.class.isAssignableFrom(configuredSource.getClass()))
            throw new UIServiceException(UIServiceErrorCode.NAVIGABLE_SOURCE_NOT_CONFIGURED);
        return (NavigableMarkdownSource) configuredSource;
    }

    @Override
    public String getContent(String sourceIdentifier, String identifier) throws SourceException, UIServiceException {
        return getRenderedContent(sourceIdentifier, identifier, (markdownSource, markdownElement) -> markdownElement);
    }

    @Override
    public String getOrDefaultContent(String sourceIdentifier, String identifier) throws UIServiceException, SourceException {
        return getRenderedContent(sourceIdentifier, identifier, (markdownSource, markdownElement) -> {
            Optional<Map.Entry<String, MarkdownElement<String>>> elementEntry1 = markdownSource.getAll().entrySet().stream().filter(stringMarkdownElementEntry -> stringMarkdownElementEntry.getValue() != null).findFirst();
            if (elementEntry1.isPresent()) {
                markdownElement = elementEntry1.get().getValue();
            }
            return markdownElement;
        });
    }

    private String getRenderedContent(String sourceIdentifier, String identifier, BiFunction<NavigableMarkdownSource, MarkdownElement<String>, MarkdownElement<String>> biFunction) throws SourceException, UIServiceException {
        NavigableMarkdownSource markdownSource = getNavigableMarkdownSource(sourceIdentifier);
        MarkdownElement<String> markdownElement = markdownSource.getMarkdownElement(identifier);
        RenderEngine renderEngine = renderEngineFactory.getConfiguredRenderEngine();
        String content = NOT_FOUND;
        if (markdownElement == null) {
            markdownElement = biFunction.apply(markdownSource, null);
        }
        if (markdownElement != null)
            content = markdownElement.getContent();
        return renderEngine.render(content);
    }
}
