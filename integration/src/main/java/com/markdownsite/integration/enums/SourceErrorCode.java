package com.markdownsite.integration.enums;

import com.markdownsite.integration.interfaces.ErrorCode;

public enum SourceErrorCode implements ErrorCode {

    SOURCE_NOT_FOUND_EXCEPTION(1, "No valid source found."),
    SOURCE_NOT_CONFIGURED_EXCEPTION(2, "No valid source configured."),
    SOURCE_ALREADY_CONFIGURED(3,"Source with the provided name is already configured.");

    private int errorCode;
    private String errorMessage;


    SourceErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

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
