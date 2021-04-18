package com.markdownsite.core.enums;

import com.markdownsite.integration.interfaces.ErrorCode;

public enum FileBasedMarkdownSourceException implements ErrorCode {
    SOURCE_DIRECTORY_NOT_CONFIGURED(1, "Valid source directory not configured.");

    FileBasedMarkdownSourceException(int errorCode, String errorMessage) {
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
