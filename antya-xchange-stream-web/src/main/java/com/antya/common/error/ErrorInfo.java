package com.antya.common.error;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorInfo {

    private final String category;
    private final String code;
    private final String message;

    public ErrorInfo(@JsonProperty("category") String category,
                     @JsonProperty("code") String code,
                     @JsonProperty("message") String message) {
        this.category = category;
        this.code = code;
        this.message = message;
    }

    public String getCategory() {
        return category;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    
    @Override
    public String toString() {
    	return ReflectionToStringBuilder.toString(this);
    }
}
