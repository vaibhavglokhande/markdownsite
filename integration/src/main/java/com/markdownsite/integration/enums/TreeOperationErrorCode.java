package com.markdownsite.integration.enums;

import com.markdownsite.integration.interfaces.ErrorCode;

public enum TreeOperationErrorCode implements ErrorCode {
    NODE_ADD_EXCEPTION(1, "Error while adding the node."),
    NODE_DELETE_EXCEPTION(2, "Error while deleting the node."),
    NODE_NOT_FOUND_EXCEPTION(3, "Node not found exception.");


    private int errorCode;
    private String errorMessage;

    TreeOperationErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getExceptionId() {
        return this.getClass().getCanonicalName();
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
