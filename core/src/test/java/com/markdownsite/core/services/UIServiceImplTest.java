package com.markdownsite.core.services;

import com.markdownsite.core.RenderEngineFactory;
import com.markdownsite.core.enums.UIServiceErrorCode;
import com.markdownsite.core.exceptions.UIServiceException;
import com.markdownsite.core.interfaces.UIService;
import com.markdownsite.integration.exceptions.AbstractException;
import com.markdownsite.integration.exceptions.SourceException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import com.markdownsite.integration.interfaces.NavigableMarkdownSource;
import com.markdownsite.integration.interfaces.RenderEngine;
import com.markdownsite.integration.models.MarkdownElement;
import com.markdownsite.integration.providers.MarkdownSourceProvider;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class UIServiceImplTest {

    @Test
    void getNavigationTree() throws AbstractException {
        MarkdownSourceProvider sourceProvider = Mockito.mock(MarkdownSourceProvider.class);

        NavigableMarkdownSource navigableMarkdownSource = Mockito.mock(NavigableMarkdownSource.class);
        Mockito.when(sourceProvider.getSource("id")).thenReturn(navigableMarkdownSource);

        RenderEngineFactory engineFactory = Mockito.mock(RenderEngineFactory.class);
        UIService uiService = new UIServiceImpl(sourceProvider, engineFactory);
        uiService.getNavigationTree("id");
        Mockito.verify(navigableMarkdownSource, Mockito.times(1)).getNavigationTree();
    }

    @Test
    void getNavigationTreeException() throws SourceException {
        MarkdownSourceProvider sourceProvider = Mockito.mock(MarkdownSourceProvider.class);

        MarkdownSource markdownSource = Mockito.mock(MarkdownSource.class);
        Mockito.when(sourceProvider.getSource("non-nav")).thenReturn(markdownSource);

        RenderEngineFactory engineFactory = Mockito.mock(RenderEngineFactory.class);
        UIService uiService = new UIServiceImpl(sourceProvider, engineFactory);
        UIServiceException uiServiceException = assertThrows(UIServiceException.class, () -> uiService.getNavigationTree("non-nav"));
        assertEquals(UIServiceErrorCode.NAVIGABLE_SOURCE_NOT_CONFIGURED, uiServiceException.getErrorCode());
    }

    @Test
    void getContent() throws AbstractException {
        MarkdownSourceProvider sourceProvider = Mockito.mock(MarkdownSourceProvider.class);

        NavigableMarkdownSource navigableMarkdownSource = Mockito.mock(NavigableMarkdownSource.class);
        Mockito.when(sourceProvider.getSource("ui")).thenReturn(navigableMarkdownSource);

        MarkdownElement markdownElement = Mockito.mock(MarkdownElement.class);
        Mockito.when(markdownElement.getContent()).thenReturn("# Title");
        Mockito.when(navigableMarkdownSource.getMarkdownElement("elementId")).thenReturn(markdownElement);

        RenderEngineFactory engineFactory = Mockito.mock(RenderEngineFactory.class);
        RenderEngine renderEngine = Mockito.mock(RenderEngine.class);
        Mockito.when(engineFactory.getConfiguredRenderEngine()).thenReturn(renderEngine);
        UIService uiService = new UIServiceImpl(sourceProvider, engineFactory);
        uiService.getContent("ui", "elementId");

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(renderEngine).render(stringArgumentCaptor.capture());
        assertEquals("# Title", stringArgumentCaptor.getValue());

    }

    @Test
    void getOrDefaultContent() throws AbstractException {
        MarkdownSourceProvider sourceProvider = Mockito.mock(MarkdownSourceProvider.class);

        NavigableMarkdownSource navigableMarkdownSource = Mockito.mock(NavigableMarkdownSource.class);
        Mockito.when(sourceProvider.getSource("ui")).thenReturn(navigableMarkdownSource);

        MarkdownElement markdownElement = Mockito.mock(MarkdownElement.class);
        Mockito.when(markdownElement.getContent()).thenReturn("# Title");
        Mockito.when(navigableMarkdownSource.getMarkdownElement("elementId")).thenReturn(markdownElement);
        Mockito.when(navigableMarkdownSource.getAll()).thenReturn(new HashMap<>(){{put("elementId",markdownElement);}});

        RenderEngineFactory engineFactory = Mockito.mock(RenderEngineFactory.class);
        RenderEngine renderEngine = Mockito.mock(RenderEngine.class);
        Mockito.when(engineFactory.getConfiguredRenderEngine()).thenReturn(renderEngine);
        UIService uiService = new UIServiceImpl(sourceProvider, engineFactory);
        uiService.getOrDefaultContent("ui", "elementId-not-present");

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(renderEngine).render(stringArgumentCaptor.capture());
        assertEquals("# Title", stringArgumentCaptor.getValue());
    }
}