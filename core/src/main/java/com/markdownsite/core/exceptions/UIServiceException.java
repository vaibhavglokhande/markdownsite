package com.markdownsite.core.exceptions;

import com.markdownsite.core.enums.UIServiceErrorCode;
import com.markdownsite.integration.exceptions.AbstractException;

public class UIServiceException extends AbstractException {
    public UIServiceException(String message, UIServiceErrorCode errorCode) {
        super(message, errorCode);
    }

    public UIServiceException(String message, Throwable cause, UIServiceErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public UIServiceException(Throwable cause, UIServiceErrorCode errorCode) {
        super(cause, errorCode);
    }

    protected UIServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, UIServiceErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }

    public UIServiceException(UIServiceErrorCode errorCode) {
        super(errorCode);
    }
}
