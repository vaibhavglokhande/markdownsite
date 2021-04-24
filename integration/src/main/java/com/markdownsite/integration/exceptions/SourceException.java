package com.markdownsite.integration.exceptions;

import com.markdownsite.integration.enums.SourceErrorCode;

public class SourceException extends AbstractException {

    public SourceException(String message, SourceErrorCode errorCode) {
        super(message, errorCode);
    }

    public SourceException(String message, Throwable cause, SourceErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public SourceException(SourceErrorCode errorCode) {
        super(errorCode);
    }
}
