package com.markdownsite.integration.enums;

import com.markdownsite.integration.interfaces.ErrorCode;

public enum SourceNotFoundErrorCode implements ErrorCode {

    SOURCE_NOT_FOUND_EXCEPTION(1, "No valid source found."),
    SOURCE_NOT_CONFIGURED_EXCEPTION(2, "No valid source configured.");

    private int errorCode;
    private String errorMessage;


    SourceNotFoundErrorCode(int errorCode, String errorMessage) {
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
