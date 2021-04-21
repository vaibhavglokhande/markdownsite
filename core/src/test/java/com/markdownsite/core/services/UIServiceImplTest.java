package com.markdownsite.core.services;

import com.markdownsite.core.RenderEngineFactory;
import com.markdownsite.core.enums.UIServiceErrorCode;
import com.markdownsite.core.exceptions.UIServiceException;
import com.markdownsite.core.interfaces.UIService;
import com.markdownsite.integration.exceptions.AbstractException;
import com.markdownsite.integration.exceptions.SourceNotFoundException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import com.markdownsite.integration.interfaces.NavigableMarkdownSource;
import com.markdownsite.integration.interfaces.RenderEngine;
import com.markdownsite.integration.models.MarkdownElement;
import com.markdownsite.integration.providers.MarkdownSourceProvider;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UIServiceImplTest {

    @Test
    void getNavigationTree() throws AbstractException {
        MarkdownSourceProvider sourceProvider = Mockito.mock(MarkdownSourceProvider.class);

        NavigableMarkdownSource navigableMarkdownSource = Mockito.mock(NavigableMarkdownSource.class);
        Mockito.when(sourceProvider.getConfiguredSource()).thenReturn(navigableMarkdownSource);

        RenderEngineFactory engineFactory = Mockito.mock(RenderEngineFactory.class);
        UIService uiService = new UIServiceImpl(sourceProvider, engineFactory);
        uiService.getNavigationTree();
        Mockito.verify(navigableMarkdownSource, Mockito.times(1)).getNavigationTree();
    }

    @Test
    void getNavigationTreeException() throws SourceNotFoundException {
        MarkdownSourceProvider sourceProvider = Mockito.mock(MarkdownSourceProvider.class);

        MarkdownSource markdownSource = Mockito.mock(MarkdownSource.class);
        Mockito.when(sourceProvider.getConfiguredSource()).thenReturn(markdownSource);

        RenderEngineFactory engineFactory = Mockito.mock(RenderEngineFactory.class);
        UIService uiService = new UIServiceImpl(sourceProvider, engineFactory);
        UIServiceException uiServiceException = assertThrows(UIServiceException.class, uiService::getNavigationTree);
        assertEquals(UIServiceErrorCode.NAVIGABLE_SOURCE_NOT_CONFIGURED, uiServiceException.getErrorCode());
    }

    @Test
    void getContent() throws AbstractException {
        MarkdownSourceProvider sourceProvider = Mockito.mock(MarkdownSourceProvider.class);

        NavigableMarkdownSource navigableMarkdownSource = Mockito.mock(NavigableMarkdownSource.class);
        Mockito.when(sourceProvider.getConfiguredSource()).thenReturn(navigableMarkdownSource);

        MarkdownElement markdownElement = Mockito.mock(MarkdownElement.class);
        Mockito.when(markdownElement.getContent()).thenReturn("# Title");
        Mockito.when(navigableMarkdownSource.getMarkdownElement("elementId")).thenReturn(markdownElement);

        RenderEngineFactory engineFactory = Mockito.mock(RenderEngineFactory.class);
        RenderEngine renderEngine = Mockito.mock(RenderEngine.class);
        Mockito.when(engineFactory.getConfiguredRenderEngine()).thenReturn(renderEngine);
        UIService uiService = new UIServiceImpl(sourceProvider, engineFactory);
        uiService.getContent("elementId");

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(renderEngine).render(stringArgumentCaptor.capture());
        assertEquals("# Title", stringArgumentCaptor.getValue());

    }
}