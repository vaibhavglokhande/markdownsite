package com.markdownsite.web;

import com.markdownsite.web.installer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationInstaller implements ApplicationRunner {

    private List<Installer> installers;
    private static final String DEFAULT_INSTALLER = "FILE";
    private static final String DEFAULT_INSTALLER_MODE = "NEW";

    @Autowired
    public ApplicationInstaller(List<Installer> installers) {
        this.installers = installers;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Installer installer = getInstaller(DEFAULT_INSTALLER);
        installer.install(InstallationMode.valueOf(DEFAULT_INSTALLER_MODE));
    }

    private Installer getInstaller(String installerType) throws InstallerException {
        for (Installer installer : installers) {
            if (installer.supports(InstallationConfig.valueOf(installerType)))
                return installer;
        }
        throw new InstallerException(InstallerErrorCode.INSTALLER_NOT_FOUND);
    }
}
