package com.markdownsite.web.installer;

import com.markdownsite.integration.exceptions.AbstractException;

public class InstallerException extends AbstractException {
    public InstallerException(String message, InstallerErrorCode errorCode) {
        super(message, errorCode);
    }

    public InstallerException(String message, Throwable cause, InstallerErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public InstallerException(Throwable cause, InstallerErrorCode errorCode) {
        super(cause, errorCode);
    }

    protected InstallerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, InstallerErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }

    public InstallerException(InstallerErrorCode errorCode) {
        super(errorCode);
    }
}
