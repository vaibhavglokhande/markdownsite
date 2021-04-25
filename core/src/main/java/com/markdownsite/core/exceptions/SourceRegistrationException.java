package com.markdownsite.core.exceptions;

import com.markdownsite.core.enums.SourceRegistrationErrorCode;
import com.markdownsite.integration.exceptions.AbstractException;

public class SourceRegistrationException extends AbstractException {
    public SourceRegistrationException(String message, SourceRegistrationErrorCode errorCode) {
        super(message, errorCode);
    }

    public SourceRegistrationException(String message, Throwable cause, SourceRegistrationErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public SourceRegistrationException(Throwable cause, SourceRegistrationErrorCode errorCode) {
        super(cause, errorCode);
    }

    protected SourceRegistrationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, SourceRegistrationErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }

    public SourceRegistrationException(SourceRegistrationErrorCode errorCode) {
        super(errorCode);
    }
}
