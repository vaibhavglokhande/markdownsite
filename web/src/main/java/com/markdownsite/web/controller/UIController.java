package com.markdownsite.web.controller;

import com.markdownsite.core.RenderEngineFactory;
import com.markdownsite.core.interfaces.ResourceProvider;
import com.markdownsite.integration.interfaces.RenderEngine;
import com.markdownsite.integration.models.RenderEngineConfigProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class UIController {

    // Hardcoding this value for time being.
    // TODO Remove this hardcoded value, use configuration.
    private String renderEngineUUID = "9971ea80-dec5-3154-af52-771b08068b97";

    private RenderEngineFactory renderEngineFactory;
    private ResourceProvider resourceProvider;


    @Autowired
    public UIController(RenderEngineFactory renderEngineFactory, ResourceProvider resourceProvider) {
        this.renderEngineFactory = renderEngineFactory;
        this.resourceProvider = resourceProvider;
    }

    @GetMapping(path = "/docs/{*docId}")
    public String renderDoc(@PathVariable String docId, Model model) {
        RenderEngine renderEngine = renderEngineFactory.getRenderEngine(renderEngineUUID);
        String markdownContent = """
                
                # This is a test
                ## Content
                                
                ```java  
                public static void main(String args[]){
                    System.out.println("This is test code.");
                }
                ```
                
                ```mermaid
                graph TD
                A[Hard] -->|Text| B(Round)
                B --> C{Decision}
                C -->|One| D[Result 1]
                C -->|Two| E[Result 2]
                ```
                
                ???+ info
                    Support for latex math. This is block code.
                    ```math
                    \\sqrt{3x-1}+(1+x)^2
                    ```
                
                ???+ tip "This is math with inline code"
                    $`a^2+b^2=c^2`$
                                
                ???+ success
                    This is dummy note
                                
                > This is block.
                
                - [ ] This is TODO-1
                - [x] This is TODO-2
                
                ### List
                
                - Item1
                - Item2
                    - Sub-item
                    
                ### Table
                
                |    Heading Centered    | Heading Left Aligned   |  Heading Centered  |   Heading Right Aligned |
                |------------------------|:-----------------------|:------------------:|------------------------:|
                | Cell text left aligned | Cell text left aligned | Cell text centered | Cell text right aligned |
                | cell 21                | cell 22                |      cell 22       |                 cell 22 |
                """;
        String render = renderEngine.render(markdownContent);
        model.addAttribute("defaultCSS", resourceProvider.getCssResources());
        model.addAttribute("defaultScript", resourceProvider.getJsResources());
        model.addAttribute("inlineCSS", resourceProvider.getInlineCssResources());
        model.addAttribute("inlineScript", resourceProvider.getInlineJsResources());
        model.addAttribute("markdownRender", render);
        return "docs/markdown-render";
    }

    private RenderEngine renderEngine() {
        return new RenderEngine() {
            @Override
            public String render(String markdownContent) {
                return null;
            }

            @Override
            public String render(File markdownFile) throws IOException {
                return null;
            }

            @Override
            public String engineName() {
                return null;
            }

            @Override
            public String engineDescription() {
                return null;
            }

            @Override
            public Map<String, RenderEngineConfigProperty> getConfig() {
                return null;
            }

            @Override
            public void updateRenderEngineConfig(Map<String, RenderEngineConfigProperty> renderEngineConfig) {

            }

            @Override
            public boolean supports(String uuid) {
                return false;
            }

            @Override
            public UUID getUUID() {
                return null;
            }
        };
    }
}
