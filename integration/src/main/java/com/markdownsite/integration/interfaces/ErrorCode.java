package com.markdownsite.integration.interfaces;

/**
 * The interface Error code.
 * The implementation of this interface provides the error information.
 */
public interface ErrorCode {


    /**
     * The code to identify the exact exception.
     *
     * @return the exception code
     */
    String getExceptionId();

    /**
     * Gets error code.
     * The error code to uniquely identify the error.
     *
     * @return the error code
     */
    int getErrorCode();

    /**
     * Gets error message.
     * The human readable error message.
     *
     * @return the error message
     */
    String getErrorMessage();

}
