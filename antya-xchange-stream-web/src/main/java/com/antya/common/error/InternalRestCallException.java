package com.antya.common.error;

public class InternalRestCallException extends RuntimeException {

    private static final long serialVersionUID = 0;

    private final int statusCode;
    private final String responseBody;

    public InternalRestCallException(int statusCode, String responseBody) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    @Override
    public String toString() {
        return "InternalRestCallException{" +
                "statusCode=" + statusCode +
                ", responseBody='" + responseBody + '\'' +
                '}';
    }
}
