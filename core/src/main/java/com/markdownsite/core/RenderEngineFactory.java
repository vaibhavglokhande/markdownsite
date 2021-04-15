package com.markdownsite.core;

import com.markdownsite.integration.interfaces.RenderEngine;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RenderEngineFactory {

    @Setter
    private List<RenderEngine> renderEngines;

    @Autowired
    public RenderEngineFactory(List<RenderEngine> renderEngines) {
        this.renderEngines = renderEngines;
    }

    public synchronized RenderEngine getRenderEngine(String engineUUID){
        Optional<RenderEngine> engineOptional = renderEngines.stream().filter(renderEngine -> renderEngine.supports(engineUUID)).findFirst();
        return engineOptional.orElse(null);
    }

    public synchronized void addRenderEngine(RenderEngine renderEngine){
        this.renderEngines.add(renderEngine);
    }

}
