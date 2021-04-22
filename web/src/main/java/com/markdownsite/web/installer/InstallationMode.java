package com.markdownsite.web.installer;

/**
 * The enum Installation mode.
 */
public enum InstallationMode {
    NEW("new"),
    UPDATE("update");

    private String mode;

    InstallationMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}
