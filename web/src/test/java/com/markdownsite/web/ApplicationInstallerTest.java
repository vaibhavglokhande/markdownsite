package com.markdownsite.web;

import com.markdownsite.web.installer.InstallationMode;
import com.markdownsite.web.installer.Installer;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationInstallerTest {


    @Test
    void testRun() throws Exception {
        Installer installer = Mockito.mock(Installer.class);
        Mockito.when(installer.supports(Mockito.any())).thenReturn(true);
        ApplicationInstaller applicationInstaller = new ApplicationInstaller(Arrays.asList(installer));
        ApplicationArguments args = new DefaultApplicationArguments();
        applicationInstaller.run(args);
        ArgumentCaptor<InstallationMode> installationModeArgumentCaptor = ArgumentCaptor.forClass(InstallationMode.class);
        Mockito.verify(installer, Mockito.times(1)).install(installationModeArgumentCaptor.capture());
        assertEquals(InstallationMode.NEW, installationModeArgumentCaptor.getValue());
    }
}