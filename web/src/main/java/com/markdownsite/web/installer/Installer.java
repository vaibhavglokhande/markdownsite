package com.markdownsite.web.installer;

/**
 * The interface Installer.
 * The implementation of this interface provides installation of the application.
 */
public interface Installer {

    /**
     * Begin the installation of the application.
     * @param installationMode
     */
    void install(InstallationMode installationMode) throws InstallerException;

    /**
     * The installation mode supported by the installer.
     *
     *
     * @param installationConfig the installation mode
     * @return the boolean true if the installation mode is supported, false otherwise
     */
    boolean supports(InstallationConfig installationConfig);

}
