package com.markdownsite.core.enums;

import com.markdownsite.integration.interfaces.ErrorCode;

public enum UIServiceErrorCode implements ErrorCode {
    NAVIGABLE_SOURCE_NOT_CONFIGURED(1, "No valid, navigable source configured.");

    UIServiceErrorCode(int errorCode, String errorMessage) {
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
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
