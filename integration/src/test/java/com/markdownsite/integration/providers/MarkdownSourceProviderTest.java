package com.markdownsite.integration.providers;

import com.markdownsite.integration.enums.SourceNotFoundErrorCode;
import com.markdownsite.integration.exceptions.SourceNotFoundException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class MarkdownSourceProviderTest {

    @Autowired
    MarkdownSourceProvider markdownSourceProvider;

    @Test
    void testGetSourceProvider() throws SourceNotFoundException {
        assertNotNull(markdownSourceProvider.getSource("mock-source"));
    }

    @Test
    void testForException() {
        SourceNotFoundException sourceNotFoundException = assertThrows(SourceNotFoundException.class, () -> markdownSourceProvider.getSource("invalid-id"));
        assertEquals(SourceNotFoundErrorCode.SOURCE_NOT_FOUND_EXCEPTION, sourceNotFoundException.getErrorCode());
    }

    @Test
    void testNoSourceConfiguredException() {
        MarkdownSourceProvider markdownSourceProvider = new MarkdownSourceProvider(null);
        SourceNotFoundException sourceNotFoundException = assertThrows(SourceNotFoundException.class, () -> markdownSourceProvider.getSource("invalid"));
        assertEquals(SourceNotFoundErrorCode.SOURCE_NOT_CONFIGURED_EXCEPTION, sourceNotFoundException.getErrorCode());
    }

    @TestConfiguration
    static class MarkdonwSourceProviderTestConfig {

        @Bean
        public Set<MarkdownSource<?>> getMarkdownSources() {
            Set<MarkdownSource<?>> markdownSources = new HashSet<>();

            MarkdownSource<?> markdownSource = Mockito.mock(MarkdownSource.class);
            Mockito.when(markdownSource.sourceIdentifier()).thenReturn("mock-source");
            markdownSources.add(markdownSource);

            return markdownSources;
        }

        @Bean
        public MarkdownSourceProvider getMarkdownSourceProvider() {
            return new MarkdownSourceProvider(getMarkdownSources());
        }

    }

}