package com.markdownsite.integration.exceptions;

import com.markdownsite.integration.interfaces.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AbstractException extends Exception {

    private final ErrorCode errorCode;

    public AbstractException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public AbstractException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public AbstractException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    protected AbstractException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public AbstractException(ErrorCode errorCode) {
        this(errorCode.getErrorMessage(), errorCode);
    }
}
