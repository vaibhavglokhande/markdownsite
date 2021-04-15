package com.markdownsite.core;

import com.markdownsite.integration.interfaces.RenderEngine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class RenderEngineFactoryTest {


    @Autowired
    private RenderEngineFactory renderEngineFactory;

    @Test
    void getRenderEngine() {
        RenderEngine renderEngine = this.renderEngineFactory.getRenderEngine("mock-uuid");
        assertNotNull(renderEngine);
        RenderEngine renderEngine2 = this.renderEngineFactory.getRenderEngine("mock-uuid-2");
        assertNotNull(renderEngine2);
        assertNotEquals(renderEngine, renderEngine2);
    }

    @Test
    void testGetNullRenderEngine() {
        RenderEngine renderEngine = this.renderEngineFactory.getRenderEngine("invalid-uuid");
        assertNull(renderEngine);
    }

    @Test
    void addRenderEngine() {
        RenderEngine mockRenderEngine = Mockito.mock(RenderEngine.class);
        Mockito.when(mockRenderEngine.supports("uuid-000-1")).thenReturn(true);
        this.renderEngineFactory.addRenderEngine(mockRenderEngine);
        RenderEngine renderEngine = this.renderEngineFactory.getRenderEngine("uuid-000-1");
        assertEquals(mockRenderEngine, renderEngine);
    }

    @TestConfiguration
    static class RenderEngineFactoryTestConfig{

        @Bean
        public List<RenderEngine> getRenderEngineList(){
            RenderEngine renderEngine = Mockito.mock(RenderEngine.class);
            RenderEngine renderEngine2 = Mockito.mock(RenderEngine.class);
            Mockito.when(renderEngine.supports(Mockito.eq("mock-uuid"))).thenReturn(true);
            Mockito.when(renderEngine2.supports(Mockito.eq("mock-uuid-2"))).thenReturn(true);
            return Stream.of(renderEngine, renderEngine2).collect(Collectors.toCollection(ArrayList::new));
        }

        @Bean
        public RenderEngineFactory renderEngineFactory(){
            RenderEngineFactory renderEngineFactory = new RenderEngineFactory(getRenderEngineList());
            renderEngineFactory.setRenderEngines(getRenderEngineList());
            return renderEngineFactory;
        }

    }
}