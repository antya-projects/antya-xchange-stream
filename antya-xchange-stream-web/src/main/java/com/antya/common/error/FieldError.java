package com.antya.common.error;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FieldError {

    private final String fieldName;
    private final String errorMessage;

    public FieldError(@JsonProperty("fieldName") String fieldName,
                      @JsonProperty("errorMessage") String errorMessage) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
    @Override
    public String toString() {
    	return ReflectionToStringBuilder.toString(this);
    }
}
