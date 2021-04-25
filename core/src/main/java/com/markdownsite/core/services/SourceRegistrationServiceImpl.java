package com.markdownsite.core.services;

import com.markdownsite.core.enums.SourceRegistrationErrorCode;
import com.markdownsite.core.exceptions.SourceRegistrationException;
import com.markdownsite.core.interfaces.SourceRegistrationService;
import com.markdownsite.integration.exceptions.PropertyValidationException;
import com.markdownsite.integration.exceptions.SourceException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import com.markdownsite.integration.interfaces.SourceRegistrationHelper;
import com.markdownsite.integration.models.SourceInfo;
import com.markdownsite.integration.models.SourceProviderConfigProperty;
import com.markdownsite.integration.providers.MarkdownSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SourceRegistrationServiceImpl implements SourceRegistrationService {

    private List<SourceRegistrationHelper> registrationHelpers;
    private MarkdownSourceProvider markdownSourceProvider;

    @Autowired
    public SourceRegistrationServiceImpl(List<SourceRegistrationHelper> registrationHelpers, MarkdownSourceProvider markdownSourceProvider) {
        this.registrationHelpers = registrationHelpers;
        this.markdownSourceProvider = markdownSourceProvider;
    }

    @Override
    public List<SourceInfo> getRegistrableSources() throws SourceRegistrationException {
        if (registrationHelpers == null)
            throw new SourceRegistrationException(SourceRegistrationErrorCode.SOURCE_UNAVAILABLE);
        return registrationHelpers.stream().map(SourceRegistrationHelper::getSourceTemplateInfo).collect(Collectors.toList());
    }

    @Override
    public void registerSource(SourceInfo sourceInfo, List<SourceProviderConfigProperty> configProperties) throws SourceRegistrationException {
        Optional<SourceRegistrationHelper> registrationHelperOptional = registrationHelpers.stream().filter(sourceRegistrationHelper -> sourceRegistrationHelper.supports(sourceInfo.getSourceId())).findFirst();
        if (registrationHelperOptional.isEmpty())
            throw new SourceRegistrationException(SourceRegistrationErrorCode.SOURCE_UNAVAILABLE);
        SourceRegistrationHelper registrationHelper = registrationHelperOptional.get();
        try {
            MarkdownSource markdownSource = registrationHelper.buildSource(sourceInfo, configProperties);
            markdownSourceProvider.registerSource(markdownSource);
        } catch (PropertyValidationException e) {
            // TODO Add logging
            throw new SourceRegistrationException(e, SourceRegistrationErrorCode.SOURCE_REGISTRATION_FAILED);
        } catch (SourceException e) {
            throw new SourceRegistrationException(e, SourceRegistrationErrorCode.SOURCE_NAME_PRESENT);
        }
    }

    @Override
    public List<SourceProviderConfigProperty> getSourceConfig(String sourceTypeIdentifier) throws SourceRegistrationException {
        Optional<SourceRegistrationHelper> registrationHelperOptional = registrationHelpers.stream().filter(sourceRegistrationHelper -> sourceRegistrationHelper.supports(sourceTypeIdentifier)).findFirst();
        if (registrationHelperOptional.isEmpty())
            throw new SourceRegistrationException(SourceRegistrationErrorCode.SOURCE_UNAVAILABLE);
        SourceRegistrationHelper registrationHelper = registrationHelperOptional.get();
        return registrationHelper.getDefaultConfigProperties();
    }
}
