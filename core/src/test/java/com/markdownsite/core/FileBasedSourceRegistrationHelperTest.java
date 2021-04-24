package com.markdownsite.core;

import com.markdownsite.integration.exceptions.PropertyValidationException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import com.markdownsite.integration.models.SourceInfo;
import com.markdownsite.integration.models.SourceProviderConfigProperty;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBasedSourceRegistrationHelperTest {

    @Test
    void supports() {
        FileBasedSourceRegistrationHelper registrationHelper = new FileBasedSourceRegistrationHelper();
        assertTrue(registrationHelper.supports("FileBasedMarkdownSource"));
    }

    @Test
    void getDefaultConfigProperties() {
        FileBasedSourceRegistrationHelper registrationHelper = new FileBasedSourceRegistrationHelper();
        List<SourceProviderConfigProperty> defaultConfigProperties = registrationHelper.getDefaultConfigProperties();
        assertEquals(2, defaultConfigProperties.size());
        assertEquals("FileSource.sourceDirectory",defaultConfigProperties.get(0).getPropertyName());
        assertEquals("FileSource.readFromClasspath", defaultConfigProperties.get(1).getPropertyName());
    }

    @Test
    void buildSource() throws PropertyValidationException {
        FileBasedSourceRegistrationHelper registrationHelper = new FileBasedSourceRegistrationHelper();
        List<SourceProviderConfigProperty> defaultConfigProperties = registrationHelper.getDefaultConfigProperties();
        defaultConfigProperties.get(0).setPropertyValue("mock-location");
        SourceInfo sourceInfo = new SourceInfo();
        sourceInfo.setSourceName("NewSource");
        sourceInfo.setSourceDescription("NewDescription");
        MarkdownSource markdownSource = registrationHelper.buildSource(sourceInfo, defaultConfigProperties);
        assertTrue(markdownSource instanceof FileBasedMarkdownSource);
        assertEquals("NewSource", markdownSource.sourceName());
        assertEquals("NewDescription", markdownSource.sourceDescription());
    }

    @Test
    void getSourceTemplateInfo() {
        FileBasedSourceRegistrationHelper registrationHelper = new FileBasedSourceRegistrationHelper();
        SourceInfo sourceTemplateInfo = registrationHelper.getSourceTemplateInfo();
        assertEquals("FileBasedMarkdownSource", sourceTemplateInfo.getSourceId());
    }
}