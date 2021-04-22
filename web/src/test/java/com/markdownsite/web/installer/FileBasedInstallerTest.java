package com.markdownsite.web.installer;

import com.markdownsite.core.interfaces.SourceConfigInitializer;
import com.markdownsite.integration.exceptions.AbstractException;
import com.markdownsite.integration.exceptions.PropertyValidationException;
import com.markdownsite.integration.exceptions.SourceNotFoundException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import com.markdownsite.integration.models.SourceProviderConfigProperty;
import com.markdownsite.integration.providers.MarkdownSourceProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FileBasedInstallerTest {

    @Test
    void testInstall() throws AbstractException {
        SourceConfigInitializer sourceConfigInitializer = Mockito.mock(SourceConfigInitializer.class);
        MarkdownSourceProvider markdownSourceProvider = Mockito.mock(MarkdownSourceProvider.class);
        MarkdownSource markdownSource = Mockito.mock(MarkdownSource.class);
        ArrayList<SourceProviderConfigProperty> configProperties = new ArrayList<>();
        Mockito.when(markdownSource.getSourceConfig()).thenReturn(configProperties);
        Mockito.when(markdownSourceProvider.getSource(Mockito.any())).thenReturn(markdownSource);

        ArrayList<SourceProviderConfigProperty> updatedProperties = new ArrayList<>();
        Mockito.when(sourceConfigInitializer.initProperties(configProperties)).thenReturn(updatedProperties);

        FileBasedInstaller fileBasedInstaller = new FileBasedInstaller(sourceConfigInitializer, markdownSourceProvider);
        fileBasedInstaller.install(InstallationMode.NEW);

        Mockito.verify(markdownSource, Mockito.times(1)).updateSourceConfig(updatedProperties);
        Mockito.verify(markdownSource, Mockito.times(1)).initializeSource();
    }

    @Test
    void testSupports() {
        FileBasedInstaller fileBasedInstaller = new FileBasedInstaller(null, null);
        assertTrue(fileBasedInstaller.supports(InstallationConfig.FILE));
        assertFalse(fileBasedInstaller.supports(InstallationConfig.DATABASE));
    }
}