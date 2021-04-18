package com.markdownsite.core;

import com.markdownsite.core.exceptions.FileBasedMarkdownSourceException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class FileBasedMarkdownSourceTest {

    @Autowired
    private MarkdownSource<String> markdownSource;

    @Test
    void sourceIdentifier() {
        assertEquals("com.markdownsite.core.FileBasedMarkdownSource", markdownSource.sourceIdentifier());
    }

    @Test
    void testInitialize() {
        FileBasedMarkdownSource fileBasedMarkdownSource = (FileBasedMarkdownSource) markdownSource;
        Path sourceDirectory = Paths.get("src", "test", "resources", "markdown-files");
        fileBasedMarkdownSource.configFileSource(sourceDirectory.toString());
        // No exception should be generated.
        assertDoesNotThrow(fileBasedMarkdownSource::initializeSource);
    }

    @TestConfiguration
    static class FileBasedMarkdownTestConfig{

        @Bean
        public FileBasedMarkdownSource getFileBasedMarkdown(){
            return new FileBasedMarkdownSource();
        }

    }
}