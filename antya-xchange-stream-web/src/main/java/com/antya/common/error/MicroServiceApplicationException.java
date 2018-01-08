package com.antya.common.error;

public class MicroServiceApplicationException extends RuntimeException {

    private static final long serialVersionUID = 0;

    private final ErrorInfo errorInfo;

    public MicroServiceApplicationException(ErrorCode code) {
        this(ErrorCategory.APPLICATION_ERROR, code.code(), "", null);
    }

    public MicroServiceApplicationException(String code) {
        this(ErrorCategory.APPLICATION_ERROR, code, "", null);
    }

    public MicroServiceApplicationException(String code, String message) {
        this(ErrorCategory.APPLICATION_ERROR, code, message, null);
    }

    public MicroServiceApplicationException(ErrorCategory category, String code) {
        this(category, code, "", null);
    }

    public MicroServiceApplicationException(ErrorCategory category, ErrorCode code) {
        this(category, code.code(), "", null);
    }

    public MicroServiceApplicationException(ErrorCategory category, ErrorCode code, String message) {
        this(category, code.code(), message, null);
    }
    
    public MicroServiceApplicationException(ErrorCategory category, String code, String message) {
        this(category, code, message, null);
    }
    
    public MicroServiceApplicationException(ErrorCategory category, String code, Throwable cause) {
        this(category, code, "", cause);
    }

    public MicroServiceApplicationException(String category, String code, String message) {
        this(category, code, message, null);
    }

    public MicroServiceApplicationException(String code, String message, Throwable cause) {
        this(ErrorCategory.APPLICATION_ERROR, code, message, cause);
    }

    public MicroServiceApplicationException(String category, String code, String message, Throwable cause) {
        super(message, cause);
        this.errorInfo = new ErrorInfo(category, code, message);
    }

    public MicroServiceApplicationException(ErrorCategory category, String code, String message, Throwable cause) {
        super(message, cause);
        this.errorInfo = new ErrorInfo(category.name(), code, message);
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }
}