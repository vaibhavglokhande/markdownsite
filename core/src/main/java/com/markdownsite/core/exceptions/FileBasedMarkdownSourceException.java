package com.markdownsite.core.exceptions;

import com.markdownsite.core.enums.FileBasedMarkdownSourceErrorCode;
import com.markdownsite.integration.exceptions.AbstractException;

public class FileBasedMarkdownSourceException extends AbstractException {
    public FileBasedMarkdownSourceException(String message, FileBasedMarkdownSourceErrorCode errorCode) {
        super(message, errorCode);
    }

    public FileBasedMarkdownSourceException(String message, Throwable cause, FileBasedMarkdownSourceErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public FileBasedMarkdownSourceException(Throwable cause, FileBasedMarkdownSourceErrorCode errorCode) {
        super(cause, errorCode);
    }

    protected FileBasedMarkdownSourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, FileBasedMarkdownSourceErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }

    public FileBasedMarkdownSourceException(FileBasedMarkdownSourceErrorCode errorCode) {
        super(errorCode);
    }
}
