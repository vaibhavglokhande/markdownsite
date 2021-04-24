package com.markdownsite.web.installer;

import com.markdownsite.core.interfaces.SourceConfigInitializer;
import com.markdownsite.integration.exceptions.AbstractException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import com.markdownsite.integration.models.SourceProviderConfigProperty;
import com.markdownsite.integration.providers.MarkdownSourceProvider;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FileBasedInstallerTest {

    @Test
    void testInstall() throws AbstractException {
        SourceConfigInitializer sourceConfigInitializer = Mockito.mock(SourceConfigInitializer.class);
        MarkdownSourceProvider markdownSourceProvider = Mockito.mock(MarkdownSourceProvider.class);
        ArrayList<SourceProviderConfigProperty> configProperties = new ArrayList<>();

        ArrayList<SourceProviderConfigProperty> updatedProperties = new ArrayList<>();
        Path path = Paths.get("src", "test", "resources", "HelpDocs");
        updatedProperties.add(new SourceProviderConfigProperty("FileSource.sourceDirectory", path.toString()));
        Mockito.when(sourceConfigInitializer.initProperties(Mockito.anyList())).thenReturn(updatedProperties);

        FileBasedInstaller fileBasedInstaller = new FileBasedInstaller(sourceConfigInitializer, markdownSourceProvider);
        fileBasedInstaller.install(InstallationMode.NEW);

        ArgumentCaptor<MarkdownSource> markdownSourceArgumentCaptor = ArgumentCaptor.forClass(MarkdownSource.class);

        Mockito.verify(markdownSourceProvider, Mockito.times(1)).registerSource(markdownSourceArgumentCaptor.capture());
        MarkdownSource markdownSource = markdownSourceArgumentCaptor.getValue();
        assertNotNull(markdownSource);
    }

    @Test
    void testSupports() {
        FileBasedInstaller fileBasedInstaller = new FileBasedInstaller(null, null);
        assertTrue(fileBasedInstaller.supports(InstallationConfig.FILE));
        assertFalse(fileBasedInstaller.supports(InstallationConfig.DATABASE));
    }
}