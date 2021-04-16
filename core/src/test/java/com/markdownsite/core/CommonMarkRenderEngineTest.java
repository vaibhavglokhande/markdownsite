package com.markdownsite.core;

import com.markdownsite.core.renderengines.commonmark.CommonMarkRenderEngine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonMarkRenderEngineTest {

    @Test
    void testSupports() {
        String uuid = "3f150efe-1aab-3680-aba3-ecacf45eab36";
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