package com.markdownsite.core;

import com.markdownsite.core.exceptions.FileBasedMarkdownSourceException;
import com.markdownsite.integration.exceptions.TreeOperationException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import com.markdownsite.integration.models.MarkdownElement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class FileBasedMarkdownSourceTest {

    @Autowired
    private MarkdownSource<String, String> markdownSource;

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

    @Test
    void testGetMarkdownElement() throws TreeOperationException, FileBasedMarkdownSourceException, IOException {
        FileBasedMarkdownSource fileBasedMarkdownSource = (FileBasedMarkdownSource) markdownSource;
        Path sourceDirectory = Paths.get("src", "test", "resources", "markdown-files");
        fileBasedMarkdownSource.configFileSource(sourceDirectory.toString());
        fileBasedMarkdownSource.initializeSource();
        Optional<File> file = Files.walk(sourceDirectory).filter(path -> path.endsWith("File1.md")).map(Path::toFile).findFirst();
        String identifier = UUID.nameUUIDFromBytes(file.get().getAbsolutePath().getBytes(StandardCharsets.UTF_8)).toString();
        MarkdownElement<String> markdownElement = fileBasedMarkdownSource.getMarkdownElement(identifier);
        assertNotNull(markdownElement);
        assertEquals("File1.md", markdownElement.getTitle());
    }

    @Test
    void testGetAllMarkdownElements() throws TreeOperationException, FileBasedMarkdownSourceException {
        FileBasedMarkdownSource fileBasedMarkdownSource = (FileBasedMarkdownSource) markdownSource;
        Path sourceDirectory = Paths.get("src", "test", "resources", "markdown-files");
        fileBasedMarkdownSource.configFileSource(sourceDirectory.toString());
        fileBasedMarkdownSource.initializeSource();
        Map<String, MarkdownElement<String>> markdownSourceAll = fileBasedMarkdownSource.getAll();
        assertEquals(6, markdownSourceAll.size());
    }

    @TestConfiguration
    static class FileBasedMarkdownTestConfig {

        @Bean
        public FileBasedMarkdownSource getFileBasedMarkdown() {
            return new FileBasedMarkdownSource();
        }

    }
}