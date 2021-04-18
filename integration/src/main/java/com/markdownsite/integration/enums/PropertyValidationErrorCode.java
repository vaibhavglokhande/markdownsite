package com.markdownsite.integration.enums;

import com.markdownsite.integration.interfaces.ErrorCode;

public enum PropertyValidationErrorCode implements ErrorCode {
    INVALID_PROPERTY(1, "Invalid property value. Property validation failed.");

    PropertyValidationErrorCode(int errorCode, String errorMessage) {
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
