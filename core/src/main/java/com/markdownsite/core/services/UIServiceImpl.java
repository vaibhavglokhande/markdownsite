package com.markdownsite.core.services;

import com.markdownsite.core.RenderEngineFactory;
import com.markdownsite.core.enums.UIServiceErrorCode;
import com.markdownsite.core.exceptions.UIServiceException;
import com.markdownsite.core.interfaces.UIService;
import com.markdownsite.integration.exceptions.SourceNotFoundException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import com.markdownsite.integration.interfaces.NavigableMarkdownSource;
import com.markdownsite.integration.interfaces.RenderEngine;
import com.markdownsite.integration.interfaces.Tree;
import com.markdownsite.integration.models.MarkdownElement;
import com.markdownsite.integration.models.SourceNavigationNode;
import com.markdownsite.integration.providers.MarkdownSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UIServiceImpl implements UIService {

    private MarkdownSourceProvider sourceProvider;
    private RenderEngineFactory renderEngineFactory;

    @Autowired
    public UIServiceImpl(MarkdownSourceProvider sourceProvider, RenderEngineFactory renderEngineFactory) {
        this.sourceProvider = sourceProvider;
        this.renderEngineFactory = renderEngineFactory;
    }

    @Override
    public Tree<SourceNavigationNode, String> getNavigationTree() throws SourceNotFoundException, UIServiceException {
        NavigableMarkdownSource markdownSource = getNavigableMarkdownSource();
        return markdownSource.getNavigationTree();
    }

    private NavigableMarkdownSource getNavigableMarkdownSource() throws SourceNotFoundException, UIServiceException {
        MarkdownSource configuredSource = sourceProvider.getConfiguredSource();
        if(!NavigableMarkdownSource.class.isAssignableFrom(configuredSource.getClass()))
            throw new UIServiceException(UIServiceErrorCode.NAVIGABLE_SOURCE_NOT_CONFIGURED);
        return (NavigableMarkdownSource) configuredSource;
    }

    @Override
    public String getContent(String identifier) throws SourceNotFoundException, UIServiceException {
        NavigableMarkdownSource markdownSource = getNavigableMarkdownSource();
        MarkdownElement<String> markdownElement = markdownSource.getMarkdownElement(identifier);
        RenderEngine renderEngine = renderEngineFactory.getConfiguredRenderEngine();
        return renderEngine.render(markdownElement.getContent());
    }
}
