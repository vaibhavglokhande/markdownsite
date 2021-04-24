package com.markdownsite.core.services;

import com.markdownsite.core.interfaces.SourceService;
import com.markdownsite.integration.exceptions.SourceException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import com.markdownsite.integration.interfaces.NavigableMarkdownSource;
import com.markdownsite.integration.models.SourceInfo;
import com.markdownsite.integration.providers.MarkdownSourceProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SourceServiceImplTest {

    @Test
    void getAllSourcesInfo() throws SourceException {
        MarkdownSourceProvider sourceProvider = Mockito.mock(MarkdownSourceProvider.class);
        MarkdownSource markdownSource = Mockito.mock(NavigableMarkdownSource.class);
        Mockito.when(markdownSource.sourceName()).thenReturn("mock-source");
        Mockito.when(markdownSource.sourceDescription()).thenReturn("mock-description");

        Map<String, MarkdownSource> markdownSourceMap = new HashMap<>();
        markdownSourceMap.put("source", markdownSource);

        Mockito.when(sourceProvider.getAllSources()).thenReturn(markdownSourceMap);

        SourceService sourceService = new SourceServiceImpl(sourceProvider);

        List<SourceInfo> allSourcesInfo = sourceService.getAllSourcesInfo();
        assertEquals(1, allSourcesInfo.size());
        SourceInfo sourceInfo = allSourcesInfo.get(0);
        assertEquals("mock-source", sourceInfo.getSourceName());
        assertEquals("source", sourceInfo.getSourceId());
        assertEquals("mock-description", sourceInfo.getSourceDescription());
    }

    @Test
    void getSourceInfo() throws SourceException {
        MarkdownSourceProvider sourceProvider = Mockito.mock(MarkdownSourceProvider.class);
        MarkdownSource markdownSource = Mockito.mock(NavigableMarkdownSource.class);
        Mockito.when(markdownSource.sourceName()).thenReturn("mock-source");
        Mockito.when(markdownSource.sourceDescription()).thenReturn("mock-description");

        Mockito.when(sourceProvider.getSource("source")).thenReturn(markdownSource);

        SourceService sourceService = new SourceServiceImpl(sourceProvider);

        SourceInfo sourceInfo = sourceService.getSourceInfo("source");
        assertEquals("mock-source", sourceInfo.getSourceName());
        assertEquals("source", sourceInfo.getSourceId());
        assertEquals("mock-description", sourceInfo.getSourceDescription());
    }
}