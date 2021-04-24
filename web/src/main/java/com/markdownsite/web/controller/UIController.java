package com.markdownsite.web.controller;

import com.markdownsite.core.interfaces.ResourceProvider;
import com.markdownsite.core.interfaces.UIService;
import com.markdownsite.integration.exceptions.AbstractException;
import com.markdownsite.integration.interfaces.Tree;
import com.markdownsite.integration.models.SourceNavigationNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UIController {

    private ResourceProvider resourceProvider;
    private UIService uiService;


    @Autowired
    public UIController(ResourceProvider resourceProvider, UIService uiService) {
        this.resourceProvider = resourceProvider;
        this.uiService = uiService;
    }

    @GetMapping(path = "/{srcId}/{docId}")
    public String renderDoc(@PathVariable String srcId, @PathVariable String docId, Model model) throws AbstractException {
        Tree<SourceNavigationNode, String> navigationTree = uiService.getNavigationTree(srcId);
        // Add the root node, as rest of the tree can be traversed using root node.
        List<SourceNavigationNode> navigationNodes = new ArrayList<>();
        navigationNodes.add(navigationTree.getRootNode());
        String content = uiService.getContent(srcId, docId);
        model.addAttribute("defaultCSS", resourceProvider.getCssResources());
        model.addAttribute("defaultScript", resourceProvider.getJsResources());
        model.addAttribute("inlineCSS", resourceProvider.getInlineCssResources());
        model.addAttribute("inlineScript", resourceProvider.getInlineJsResources());
        model.addAttribute("markdownRender", content);
        model.addAttribute("nodes", navigationNodes);
        return "docs/markdown-render";
    }

}
