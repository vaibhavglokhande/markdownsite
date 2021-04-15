package com.markdownsite.core.renderengines.flexmark;

import com.markdownsite.integration.interfaces.RenderEngine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaterialFlexMarkRenderEngineTest {

    @Test
    void supports() {
        String expectedUUID = "9971ea80-dec5-3154-af52-771b08068b97";
        RenderEngine renderEngine = new MaterialFlexMarkRenderEngine();
        assertTrue(renderEngine.supports(expectedUUID));
    }

    @Test
    void getUUID() {
        RenderEngine renderEngine = new MaterialFlexMarkRenderEngine();
        assertNotNull(renderEngine.getUUID());
    }
}