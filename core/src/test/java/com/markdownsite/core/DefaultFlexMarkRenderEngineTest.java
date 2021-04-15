package com.markdownsite.core;

import com.markdownsite.core.renderengines.flexmark.DefaultFlexMarkRenderEngine;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class DefaultFlexMarkRenderEngineTest {
    @Test
    void testRender() {
        String inputMarkdown = "# Test";
        DefaultFlexMarkRenderEngine defaultFlexMarkRenderEngine = new DefaultFlexMarkRenderEngine();
        String render = defaultFlexMarkRenderEngine.render(inputMarkdown);
        assertEquals("<h1>Test</h1>\n", render);
    }

    @Test
    void testFileRender() throws IOException {
        String expectedValue = "<h1>Test</h1>\n" +
                "<h2>Description</h2>\n" +
                "<p>This is a test file used for testing.</p>\n" +
                "<div class=\"mermaid\">\n" +
                "</div>\n";
        Path path = Paths.get("src", "test", "resources", "test-markdown.md");
        File file = path.toFile();
        DefaultFlexMarkRenderEngine defaultFlexMarkRenderEngine = new DefaultFlexMarkRenderEngine();
        String render = defaultFlexMarkRenderEngine.render(file);
        assertEquals(expectedValue, render);
    }

    @Test
    void testSupports() {
        String expectedUUID = "2a5b00b7-ab99-3a52-b60a-e2dbb8097e91";
        DefaultFlexMarkRenderEngine defaultFlexMarkRenderEngine = new DefaultFlexMarkRenderEngine();
        assertTrue(defaultFlexMarkRenderEngine.supports(expectedUUID));
    }

    @Test
    void testGetUUID() {
        DefaultFlexMarkRenderEngine defaultFlexMarkRenderEngine = new DefaultFlexMarkRenderEngine();
        assertNotNull(defaultFlexMarkRenderEngine.getUUID());
    }
}