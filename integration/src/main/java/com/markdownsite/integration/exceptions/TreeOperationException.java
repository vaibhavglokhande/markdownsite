package com.markdownsite.integration.exceptions;

import com.markdownsite.integration.interfaces.ErrorCode;

public class TreeOperationException extends AbstractException{
    public TreeOperationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public TreeOperationException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public TreeOperationException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    protected TreeOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }

    public TreeOperationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
