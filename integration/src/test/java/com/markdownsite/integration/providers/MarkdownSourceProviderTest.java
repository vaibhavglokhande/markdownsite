package com.markdownsite.integration.providers;

import com.markdownsite.integration.enums.SourceErrorCode;
import com.markdownsite.integration.exceptions.SourceException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class MarkdownSourceProviderTest {

    @Autowired
    MarkdownSourceProvider markdownSourceProvider;

    @Test
    void testGetSourceProvider() throws SourceException {
        assertNotNull(markdownSourceProvider.getSource("mock-source"));
    }

    @Test
    void testForException() {
        SourceException sourceException = assertThrows(SourceException.class, () -> markdownSourceProvider.getSource("invalid-id"));
        assertEquals(SourceErrorCode.SOURCE_NOT_FOUND_EXCEPTION, sourceException.getErrorCode());
    }

    @Test
    void testNoSourceConfiguredException() {
        MarkdownSourceProvider markdownSourceProvider = new MarkdownSourceProvider(null);
        SourceException sourceException = assertThrows(SourceException.class, () -> markdownSourceProvider.getSource("invalid"));
        assertEquals(SourceErrorCode.SOURCE_NOT_CONFIGURED_EXCEPTION, sourceException.getErrorCode());
    }

    @TestConfiguration
    static class MarkdonwSourceProviderTestConfig {

        @Bean
        public Map<String, MarkdownSource> getMarkdownSources() {
            Map<String, MarkdownSource> markdownSources = new HashMap<>();

            MarkdownSource markdownSource = Mockito.mock(MarkdownSource.class);
            Mockito.when(markdownSource.sourceName()).thenReturn("mock-source");
            markdownSources.put("mock-source",markdownSource);

            return markdownSources;
        }

        @Bean
        public MarkdownSourceProvider getMarkdownSourceProvider() {
            return new MarkdownSourceProvider(getMarkdownSources());
        }

    }

}