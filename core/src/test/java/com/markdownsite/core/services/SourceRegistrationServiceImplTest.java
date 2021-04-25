package com.markdownsite.core.services;

import com.markdownsite.core.enums.SourceRegistrationErrorCode;
import com.markdownsite.core.exceptions.SourceRegistrationException;
import com.markdownsite.core.interfaces.SourceRegistrationService;
import com.markdownsite.integration.exceptions.AbstractException;
import com.markdownsite.integration.exceptions.PropertyValidationException;
import com.markdownsite.integration.exceptions.SourceException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import com.markdownsite.integration.interfaces.SourceRegistrationHelper;
import com.markdownsite.integration.models.SourceInfo;
import com.markdownsite.integration.models.SourceProviderConfigProperty;
import com.markdownsite.integration.providers.MarkdownSourceProvider;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SourceRegistrationServiceImplTest {

    @Test
    void getRegistrableSources() throws SourceRegistrationException, SourceException {
        SourceRegistrationHelper registrationHelper = Mockito.mock(SourceRegistrationHelper.class);
        Mockito.when(registrationHelper.getSourceTemplateInfo()).thenReturn(new SourceInfo());
        MarkdownSourceProvider markdownSourceProvider = Mockito.mock(MarkdownSourceProvider.class);
        SourceRegistrationService registrationService = new SourceRegistrationServiceImpl(Arrays.asList(registrationHelper), markdownSourceProvider);
        List<SourceInfo> registrableSources = registrationService.getRegistrableSources();
        assertNotNull(registrableSources);
        assertEquals(1, registrableSources.size());
    }

    @Test
    void getRegistrableSourcesException() {
        SourceRegistrationService registrationService = new SourceRegistrationServiceImpl(null, null);
        SourceRegistrationException sourceRegistrationException = assertThrows(SourceRegistrationException.class, () -> registrationService.getRegistrableSources());
        assertEquals(SourceRegistrationErrorCode.SOURCE_UNAVAILABLE, sourceRegistrationException.getErrorCode());
    }

    @Test
    void registerSource() throws AbstractException {
        SourceRegistrationHelper registrationHelper = Mockito.mock(SourceRegistrationHelper.class);
        Mockito.when(registrationHelper.supports(Mockito.anyString())).thenReturn(true);
        MarkdownSource markdownSource = Mockito.mock(MarkdownSource.class);
        Mockito.when(registrationHelper.buildSource(Mockito.any(), Mockito.any())).thenReturn(markdownSource);
        MarkdownSourceProvider markdownSourceProvider = Mockito.mock(MarkdownSourceProvider.class);
        SourceRegistrationService registrationService = new SourceRegistrationServiceImpl(Arrays.asList(registrationHelper), markdownSourceProvider);

        SourceInfo sourceInfo = new SourceInfo();
        sourceInfo.setSourceId("mock-id");

        registrationService.registerSource(sourceInfo, Mockito.anyList());

        Mockito.verify(markdownSource, Mockito.times(1)).initializeSource();

        Mockito.verify(markdownSourceProvider, Mockito.times(1)).registerSource(ArgumentMatchers.any(MarkdownSource.class));
    }

    @Test
    void getSourceConfig() throws SourceRegistrationException {
        SourceRegistrationHelper registrationHelper = Mockito.mock(SourceRegistrationHelper.class);
        Mockito.when(registrationHelper.supports(Mockito.anyString())).thenReturn(true);
        List<SourceProviderConfigProperty> configProperties = Mockito.mock(List.class);
        Mockito.when(registrationHelper.getDefaultConfigProperties()).thenReturn(configProperties);
        MarkdownSourceProvider markdownSourceProvider = Mockito.mock(MarkdownSourceProvider.class);
        SourceRegistrationService registrationService = new SourceRegistrationServiceImpl(Arrays.asList(registrationHelper), markdownSourceProvider);
        List<SourceProviderConfigProperty> sourceConfig = registrationService.getSourceConfig("mock-id");
        assertEquals(configProperties, sourceConfig);
    }
}