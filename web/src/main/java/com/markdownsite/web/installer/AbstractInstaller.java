package com.markdownsite.web.installer;

public abstract class AbstractInstaller implements Installer{
    @Override
    public final void install(InstallationMode installationMode) throws InstallerException {
        beginInstallation(installationMode);
        registerHelpSource();
    }

    protected abstract void beginInstallation(InstallationMode installationMode) throws InstallerException;

    protected abstract void registerHelpSource() throws InstallerException;
}
