package com.markdownsite.web.installer;

import com.markdownsite.integration.interfaces.ErrorCode;

public enum InstallerErrorCode implements ErrorCode {
    INSTALLER_MODE_NOT_SUPPORTED(1, "The provided installation mode is not supported by this installer."),
    INSTALLER_NOT_FOUND(2, "No installer found.");

    InstallerErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    private int errorCode;
    private String errorMessage;

    @Override
    public String getExceptionId() {
        return this.getClass().getCanonicalName();
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
