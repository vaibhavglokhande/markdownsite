package com.markdownsite.core.enums;

import com.markdownsite.integration.interfaces.ErrorCode;

public enum SourceRegistrationErrorCode implements ErrorCode {
    SOURCE_NAME_PRESENT(1, "Source with given name already registered."),
    SOURCE_PROPERTY_VALIDATION(2, "Provided property values are invalid."),
    SOURCE_REGISTRATION_FAILED(3, "Source registration failed."),
    SOURCE_UNAVAILABLE(4,"No valid source available for registration.");

    SourceRegistrationErrorCode(int errorCode, String errorMessage) {
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
