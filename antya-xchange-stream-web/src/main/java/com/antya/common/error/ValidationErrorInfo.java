package com.antya.common.error;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ValidationErrorInfo extends ErrorInfo {

    private final List<FieldError> fieldErrors;

    public ValidationErrorInfo(@JsonProperty("category") String category,
                               @JsonProperty("code") String code,
                               @JsonProperty("message") String message,
                               @JsonProperty("fieldErrors") List<FieldError> fieldErrors) {
        super(category, code, message);
        this.fieldErrors = fieldErrors;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
    
    @Override
    public String toString() {
    	return ReflectionToStringBuilder.toString(this);
    }
}
