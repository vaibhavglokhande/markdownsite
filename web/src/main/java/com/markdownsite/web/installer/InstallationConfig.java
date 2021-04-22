package com.markdownsite.web.installer;

/**
 * The enum Installation config.
 * The installation config defines how the installation config is provided to the installer.
 */
public enum InstallationConfig {
    /**
     * Database installation config.
     */
    DATABASE("DB"),
    /**
     * File installation config.
     */
    FILE("FILE");
    private String config;

    InstallationConfig(String config) {
        this.config = config;
    }

    /**
     * Gets mode.
     *
     * @return the mode
     */
    public String getConfig() {
        return config;
    }
}
