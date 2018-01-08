package com.antya.common.error;

import org.springframework.validation.BindingResult;

public class BindingValidationException extends RuntimeException {

	private static final long serialVersionUID = -1620765894776874357L;
	private BindingResult bindingResult;

    public BindingValidationException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

}
