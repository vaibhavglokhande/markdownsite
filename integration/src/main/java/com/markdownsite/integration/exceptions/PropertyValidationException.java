package com.markdownsite.integration.exceptions;

import com.markdownsite.integration.interfaces.ErrorCode;

public class PropertyValidationException extends AbstractException {
    public PropertyValidationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public PropertyValidationException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public PropertyValidationException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    protected PropertyValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }

    public PropertyValidationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
