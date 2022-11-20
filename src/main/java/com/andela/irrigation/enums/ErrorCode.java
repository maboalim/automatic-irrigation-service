package com.andela.irrigation.enums;

public enum ErrorCode {
    RESOURCE_NOT_FOUND( "404","Resource Not Found"),
    UNEXPECTED_ERROR( "410","Unexpected Error"),

    UNSUPPORTED_MEDIA_TYPE( "415","Unsupported Media Type");

    private String statusCode;
    private String errorMessage;

    ErrorCode(String statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
