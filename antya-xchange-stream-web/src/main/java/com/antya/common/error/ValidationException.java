package com.antya.common.error;

import java.util.List;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -1290109249682667573L;
	private final List<FieldError> fieldErrors;

    public ValidationException(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}
