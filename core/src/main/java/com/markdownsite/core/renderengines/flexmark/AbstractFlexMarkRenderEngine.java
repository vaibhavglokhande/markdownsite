package com.markdownsite.core.renderengines.flexmark;

import com.markdownsite.integration.interfaces.RenderEngine;
import com.markdownsite.integration.interfaces.ResourceConfig;
import com.markdownsite.integration.models.RenderEngineConfigProperty;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.admonition.AdmonitionExtension;
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.gitlab.GitLabExtension;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.ext.macros.MacrosExtension;
import com.vladsch.flexmark.ext.media.tags.MediaTagsExtension;
import com.vladsch.flexmark.ext.superscript.SuperscriptExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.jira.converter.JiraConverterExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.misc.Extension;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractFlexMarkRenderEngine implements RenderEngine, ResourceConfig {

    protected Map<String, RenderEngineConfigProperty> renderEngineConfig;


    public AbstractFlexMarkRenderEngine() {
        this.renderEngineConfig = new ConcurrentHashMap<>();
        this.renderEngineConfig.putAll(getDefaultExtensionConfig());
    }

    @Override
    public String render(String markdownContent) {
        Parser build = Parser.builder(getOptions()).build();
        Document parse = build.parse(markdownContent);
        return getRenderedString(parse);
    }

    private String getRenderedString(Document parse) {
        HtmlRenderer htmlRenderer = HtmlRenderer.builder(getOptions()).build();
        return htmlRenderer.render(parse);
    }

    private MutableDataSet getOptions() {
        MutableDataSet options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, getExtensions());
        options.set(HtmlRenderer.GENERATE_HEADER_ID, true);
        return options;
    }

    @Override
    public String render(File markdownFile) throws IOException {
        Parser parser = Parser.builder(getOptions()).build();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(markdownFile));

        Document parsedDocument = parser.parseReader(bufferedReader);

        return getRenderedString(parsedDocument);
    }

    @Override
    public String engineName() {
        return "FlexMarkEngine";
    }

    @Override
    public String engineDescription() {
        return """
                This engine is based on the FlexMark-java project.
                Please refer to the link for more information - https://github.com/vsch/flexmark-java
                """;
    }

    @Override
    public Map<String, RenderEngineConfigProperty> getConfig() {
        return this.renderEngineConfig;
    }

    @Override
    public void updateRenderEngineConfig(Map<String, RenderEngineConfigProperty> renderEngineConfig) {
        synchronized (this) {
            this.renderEngineConfig = renderEngineConfig;
        }
    }

    private List<Extension> getExtensions() {
        List<Extension> extensions = new ArrayList<>();
        if (this.renderEngineConfig != null) {
            this.renderEngineConfig.forEach((propertyName, renderEngineConfigProperty) -> {
                Class propertyClass = (Class) renderEngineConfigProperty.getPropertyValue();
                if (renderEngineConfigProperty.isEnabled() && Extension.class.isAssignableFrom(propertyClass)) {
                    try {
                        Method createInstance = propertyClass.getMethod("create");
                        extensions.add((Extension) createInstance.invoke(null));
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        // TODO - Log the error.
                    }
                }
            });
        }
        return extensions;
    }

    private Map<String, RenderEngineConfigProperty<Class<? extends Extension>>> getDefaultExtensionConfig() {
        Map<String, RenderEngineConfigProperty<Class<? extends Extension>>> configPropertyMap = new HashMap<>();
        configPropertyMap.put("Abbreviation Extension", new RenderEngineConfigProperty<>("Abbreviation", AbbreviationExtension.class, true));
        configPropertyMap.put("Admonition Extension", new RenderEngineConfigProperty<>("Admonition", AdmonitionExtension.class, true));
        configPropertyMap.put("AnchorLink Extension", new RenderEngineConfigProperty<>("AnchorLink", AnchorLinkExtension.class, false));
        configPropertyMap.put("Aside Extension", new RenderEngineConfigProperty<>("Aside", TablesExtension.class, true));
        configPropertyMap.put("Attributes Extension", new RenderEngineConfigProperty<>("Attributes", AttributesExtension.class, true));
        configPropertyMap.put("AutoLink Extension", new RenderEngineConfigProperty<>("AutoLink", AutolinkExtension.class, true));
        configPropertyMap.put("Definition List Extension", new RenderEngineConfigProperty<>("DefinitionList", DefinitionExtension.class, true));
        configPropertyMap.put("Emoji Extension", new RenderEngineConfigProperty<>("Aside", EmojiExtension.class, false));
        configPropertyMap.put("JekyllTagExtension", new RenderEngineConfigProperty<>("JekyllTag", JekyllTagExtension.class, true));
        configPropertyMap.put("Strikethrough and Subscript Extension", new RenderEngineConfigProperty<>("Strikethrough", StrikethroughSubscriptExtension.class, false));
        configPropertyMap.put("TaskList Extension", new RenderEngineConfigProperty<>("TaskList", TaskListExtension.class, true));
        configPropertyMap.put("GitLab Extension", new RenderEngineConfigProperty<>("GitLab", GitLabExtension.class, true));
        configPropertyMap.put("Jira Converter Extension", new RenderEngineConfigProperty<>("JiraConverter", JiraConverterExtension.class, false));
        configPropertyMap.put("Macros Extension", new RenderEngineConfigProperty<>("Macros", MacrosExtension.class, true));
        configPropertyMap.put("Media Extension", new RenderEngineConfigProperty<>("Media", MediaTagsExtension.class, true));
        configPropertyMap.put("Superscript Extension", new RenderEngineConfigProperty<>("Superscript", SuperscriptExtension.class, true));
        configPropertyMap.put("Table Extension", new RenderEngineConfigProperty<>("Table", TablesExtension.class, true));
        configPropertyMap.put("Wiki Links Extension", new RenderEngineConfigProperty<>("Wiki", WikiLinkExtension.class, true));
        configPropertyMap.put("Yaml front matter Extension", new RenderEngineConfigProperty<>("Yaml", YamlFrontMatterExtension.class, true));
        return configPropertyMap;
    }

    @Override
    public List<String> getJsResources() {
        List<String> jsResources = new ArrayList<>();
        jsResources.add("https://cdn.jsdelivr.net/npm/mermaid/dist/mermaid.min.js");
        jsResources.add("https://cdn.jsdelivr.net/npm/katex@0.13.2/dist/katex.min.js");
        return jsResources;
    }

    @Override
    public List<String> getCssResources() {
        List<String> cssResources = new ArrayList<>();
        cssResources.add("https://cdn.jsdelivr.net/npm/katex@0.13.2/dist/katex.min.css");
        return cssResources;
    }

    @Override
    public List<String> getInlineJsResources() {

        List<String> jsResources = new ArrayList<>();
        jsResources.add("mermaid.initialize({startOnLoad:true});");
        jsResources.add("""
                (function () {
                      document.addEventListener("DOMContentLoaded", function () {
                        var mathElems = document.getElementsByClassName("katex");
                        var elems = [];
                        for (const i in mathElems) {
                            if (mathElems.hasOwnProperty(i)) elems.push(mathElems[i]);
                        }
                                
                        elems.forEach(elem => {
                            katex.render(elem.textContent, elem, { throwOnError: false, displayMode: elem.nodeName !== 'SPAN', });
                        });
                    });
                })();
                """);
        jsResources.add(AdmonitionExtension.getDefaultScript());
        return jsResources;
    }

    @Override
    public List<String> getInlineCssResources() {
        return Arrays.asList(AdmonitionExtension.getDefaultCSS());
    }
}
