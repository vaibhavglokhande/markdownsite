package com.markdownsite.web.controller;

import com.markdownsite.core.interfaces.ResourceProvider;
import com.markdownsite.core.interfaces.SourceService;
import com.markdownsite.core.interfaces.UIService;
import com.markdownsite.integration.exceptions.AbstractException;
import com.markdownsite.integration.exceptions.SourceException;
import com.markdownsite.integration.interfaces.Tree;
import com.markdownsite.integration.models.SourceInfo;
import com.markdownsite.integration.models.SourceNavigationNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/docs")
public class UIController {

    private ResourceProvider resourceProvider;
    private UIService uiService;
    private SourceService sourceService;

    @Autowired
    public UIController(ResourceProvider resourceProvider, UIService uiService, SourceService sourceService) {
        this.resourceProvider = resourceProvider;
        this.uiService = uiService;
        this.sourceService = sourceService;
    }

    @RequestMapping(path = {"", "/"})
    public String showSourceList(Model model) throws SourceException {
        populateModelWithResources(model);
        List<SourceInfo> allSourcesInfo = this.sourceService.getAllSourcesInfo();
        model.addAttribute("sources", allSourcesInfo);
        return "docs/sources";
    }

    @GetMapping(path = {"/{srcId}/", "/{srcId}/{docId}"})
    public String renderDoc(@PathVariable String srcId, @PathVariable(name = "docId", required = false) Optional<String> docId, Model model) throws AbstractException {
        Tree<SourceNavigationNode, String> navigationTree = uiService.getNavigationTree(srcId);
        String content = uiService.getOrDefaultContent(srcId, docId.orElse("index"));
        populateModelWithResources(model);
        model.addAttribute("markdownRender", content);
        model.addAttribute("nodes", navigationTree.getRootNode());
        model.addAttribute("sourceId", srcId);
        return "docs/markdown-render";
    }

    private void populateModelWithResources(Model model) {
        model.addAttribute("defaultCSS", resourceProvider.getCssResources());
        model.addAttribute("defaultScript", resourceProvider.getJsResources());
        model.addAttribute("inlineCSS", resourceProvider.getInlineCssResources());
        model.addAttribute("inlineScript", resourceProvider.getInlineJsResources());
    }

}
