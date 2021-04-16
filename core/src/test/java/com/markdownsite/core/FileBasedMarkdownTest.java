package com.markdownsite.core;

import com.markdownsite.integration.interfaces.MarkdownSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class FileBasedMarkdownTest {

    @Autowired
    private MarkdownSource<?> markdownSource;

    @Test
    void sourceIdentifier() {
        assertEquals("com.markdownsite.core.FileBasedMarkdown", markdownSource.sourceIdentifier());
    }

    @TestConfiguration
    static class FileBasedMarkdownTestConfig{

        @Bean
        public FileBasedMarkdown getFileBasedMarkdown(){
            return new FileBasedMarkdown();
        }

    }
}