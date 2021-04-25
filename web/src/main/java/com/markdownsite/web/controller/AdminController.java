package com.markdownsite.web.controller;

import com.markdownsite.core.exceptions.SourceRegistrationException;
import com.markdownsite.core.interfaces.ResourceProvider;
import com.markdownsite.core.interfaces.SourceRegistrationService;
import com.markdownsite.integration.models.SourceInfo;
import com.markdownsite.integration.models.SourceProviderConfigProperty;
import com.markdownsite.web.models.SourceRegistrationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private SourceRegistrationService registrationService;
    private ResourceProvider resourceProvider;

    @Autowired
    public AdminController(SourceRegistrationService registrationService, ResourceProvider resourceProvider) {
        this.registrationService = registrationService;
        this.resourceProvider = resourceProvider;
    }

    @GetMapping("/register-source")
    public String getRegistrationSelectPage(Model model) throws SourceRegistrationException {
        populateModelWithResources(model);
        List<SourceInfo> registrableSources = registrationService.getRegistrableSources();
        model.addAttribute("sourcesList", registrableSources);
        model.addAttribute("sourceInfo", new SourceInfo());
        return "admin/sourceList";
    }

    @GetMapping(value = "/register-source", params = "sourceId")
    public String showRegistrationPage(Model model, SourceInfo sourceInfo) throws SourceRegistrationException {
        populateModelWithResources(model);
        List<SourceProviderConfigProperty> sourceConfig = registrationService.getSourceConfig(sourceInfo.getSourceId());

        SourceRegistrationModel sourceRegistrationModel = new SourceRegistrationModel();
        sourceRegistrationModel.setSourceInfo(sourceInfo);
        sourceRegistrationModel.setConfigProperties(sourceConfig);

        model.addAttribute("registrationModel", sourceRegistrationModel);

        return "admin/sourceRegistrationForm";
    }

    @PostMapping(value = "/register-source")
    public String registerSource(Model model, SourceRegistrationModel registrationModel) throws SourceRegistrationException {
        populateModelWithResources(model);
        registrationService.registerSource(registrationModel.getSourceInfo(), registrationModel.getConfigProperties());
        return "admin/sourceRegistrationComplete";
    }

    private void populateModelWithResources(Model model) {
        model.addAttribute("defaultCSS", resourceProvider.getCssResources());
        model.addAttribute("defaultScript", resourceProvider.getJsResources());
        model.addAttribute("inlineCSS", resourceProvider.getInlineCssResources());
        model.addAttribute("inlineScript", resourceProvider.getInlineJsResources());
    }
}
