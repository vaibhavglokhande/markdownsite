package com.markdownsite.integration.exceptions;

import com.markdownsite.integration.interfaces.ErrorCode;

public class SourceNotFoundException extends AbstractException {

    public SourceNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public SourceNotFoundException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public SourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
