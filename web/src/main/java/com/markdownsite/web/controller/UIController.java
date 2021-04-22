package com.markdownsite.web.controller;

import com.markdownsite.core.FileBasedMarkdownSource;
import com.markdownsite.core.RenderEngineFactory;
import com.markdownsite.core.interfaces.ResourceProvider;
import com.markdownsite.core.interfaces.UIService;
import com.markdownsite.integration.exceptions.AbstractException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import com.markdownsite.integration.interfaces.RenderEngine;
import com.markdownsite.integration.interfaces.Tree;
import com.markdownsite.integration.models.MarkdownElement;
import com.markdownsite.integration.models.SourceNavigationNode;
import com.markdownsite.integration.models.SourceProviderConfigProperty;
import com.markdownsite.integration.providers.MarkdownSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class UIController {

    // Hardcoding this value for time being.
    // TODO Remove this hardcoded value, use configuration.
    private String renderEngineUUID = "9971ea80-dec5-3154-af52-771b08068b97";

    private ResourceProvider resourceProvider;
    private UIService uiService;


    @Autowired
    public UIController(ResourceProvider resourceProvider, UIService uiService) {
        this.resourceProvider = resourceProvider;
        this.uiService = uiService;
    }

    @GetMapping(path = "/docs/{docId}")
    public String renderDoc(@PathVariable String docId, Model model) throws AbstractException {
        Tree<SourceNavigationNode, String> navigationTree = uiService.getNavigationTree();
        String content = uiService.getContent(docId);
        model.addAttribute("defaultCSS", resourceProvider.getCssResources());
        model.addAttribute("defaultScript", resourceProvider.getJsResources());
        model.addAttribute("inlineCSS", resourceProvider.getInlineCssResources());
        model.addAttribute("inlineScript", resourceProvider.getInlineJsResources());
        model.addAttribute("markdownRender", content);
        return "docs/markdown-render";
    }

}
