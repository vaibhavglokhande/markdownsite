package com.markdownsite.core;

import com.markdownsite.core.renderengines.commonmark.CommonMarkRenderEngine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonMarkRenderEngineTest {

    @Test
    void testSupports() {
        String uuid = "08ad77e4-f723-3053-89ef-2d63478baa53";
        CommonMarkRenderEngine commonMarkRenderEngine = new CommonMarkRenderEngine();
        assertTrue(commonMarkRenderEngine.supports(uuid));
    }

    @Test
    void testDoesNotSupport() {
        String uuid = "invalid-000-1";
        CommonMarkRenderEngine commonMarkRenderEngine = new CommonMarkRenderEngine();
        assertFalse(commonMarkRenderEngine.supports(uuid));
    }
}