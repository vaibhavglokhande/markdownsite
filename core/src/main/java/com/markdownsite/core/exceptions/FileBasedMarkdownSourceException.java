package com.markdownsite.core.exceptions;

import com.markdownsite.integration.exceptions.AbstractException;
import com.markdownsite.integration.interfaces.ErrorCode;

public class FileBasedMarkdownSourceException extends AbstractException {
    public FileBasedMarkdownSourceException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public FileBasedMarkdownSourceException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public FileBasedMarkdownSourceException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    protected FileBasedMarkdownSourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }

    public FileBasedMarkdownSourceException(ErrorCode errorCode) {
        super(errorCode);
    }
}
