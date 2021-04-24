package com.markdownsite.web.config;

import com.markdownsite.integration.interfaces.ResourceConfig;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultResourceConfig  implements ResourceConfig {

    private ServletContext servletContext;

    @Autowired
    public DefaultResourceConfig(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public List<String> getJsResources() {
        List<String> jsResources = new ArrayList<>();
        jsResources.add("https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js");
        return jsResources;
    }

    @Override
    public List<String> getCssResources() {
        List<String> cssResources = new ArrayList<>();
        cssResources.add("https://unpkg.com/material-components-web@latest/dist/material-components-web.min.css");
        cssResources.add(getApplicationContextPath() + "/css/custom.css");
        return cssResources;
    }

    private String getApplicationContextPath() {
        String contextPath = servletContext.getContextPath();
        return (contextPath == null || contextPath.isBlank())? "" : "/" + contextPath;
    }

    @Override
    public List<String> getInlineJsResources() {
       return null;
    }

    @Override
    public List<String> getInlineCssResources() {
        return null;
    }
}
