package com.antya.xchange.stream;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.antya.common.error.BindingValidationException;
import com.antya.common.error.ErrorCategory;
import com.antya.common.error.ErrorInfo;
import com.antya.common.error.FieldError;
import com.antya.common.error.InternalRestCallException;
import com.antya.common.error.MicroServiceApplicationException;
import com.antya.common.error.ValidationErrorInfo;
import com.antya.common.error.ValidationException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestControllerAdvice
public class DefaultExceptionHandler {

	private final Logger LOG = LogManager.getLogger(this.getClass());

	private static final String EMPTY_STRING = "";

	@RequestMapping()
	@ExceptionHandler({ MissingServletRequestParameterException.class,
			UnsatisfiedServletRequestParameterException.class, HttpRequestMethodNotSupportedException.class,
			ServletRequestBindingException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorInfo handleRequestException(Exception ex) {
		ErrorInfo errorInfo = new ErrorInfo(ErrorCategory.REQUEST_ERROR.name(), ErrorCategory.REQUEST_ERROR.name(),
				EMPTY_STRING);
		LOG.info("Bad Request: " + errorInfo.toString(), ex);
		return errorInfo;
	}

	@RequestMapping()
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorInfo handleValidationException(ConstraintViolationException ex) {
		List<FieldError> fieldErrors = ex.getConstraintViolations().stream()
				.map(cv -> new FieldError(cv.getPropertyPath().toString(), cv.getMessage()))
				.collect(Collectors.toList());
		ValidationErrorInfo errorInfo = new ValidationErrorInfo(ErrorCategory.VALIDATION_ERROR.name(),
				"constraint.violation", EMPTY_STRING, fieldErrors);
		LOG.info("Constraint violation: " + errorInfo.toString(), ex);
		return errorInfo;
	}

	@RequestMapping()
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorInfo handleMethodValidationException(MethodArgumentNotValidException ex) {
		List<FieldError> fieldErrors = getFieldErrorsFromBindingResult(ex.getBindingResult());
		ValidationErrorInfo errorInfo = new ValidationErrorInfo(ErrorCategory.VALIDATION_ERROR.name(),
				"method.argument.not.valid", EMPTY_STRING, fieldErrors);
		LOG.info("Method argument violation: " + errorInfo.toString(), ex);
		return errorInfo;
	}

	@RequestMapping()
	@ExceptionHandler(BindingValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorInfo handleValidationException(BindingValidationException ex) {
		List<FieldError> fieldErrors = getFieldErrorsFromBindingResult(ex.getBindingResult());
		ValidationErrorInfo errorInfo = new ValidationErrorInfo(ErrorCategory.VALIDATION_ERROR.name(),
				"binding.validation", EMPTY_STRING, fieldErrors);
		LOG.info("Validation failed: " + errorInfo.toString(), ex);
		return errorInfo;
	}

	@RequestMapping()
	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorInfo handleValidationException(ValidationException ex) {
		ValidationErrorInfo errorInfo = new ValidationErrorInfo(ErrorCategory.VALIDATION_ERROR.name(), "validation",
				EMPTY_STRING, ex.getFieldErrors());
		LOG.info("Validation failed: " + errorInfo.toString(), ex);
		return errorInfo;
	}

	@RequestMapping()
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public void handleUnsupportedMediaTypeException(HttpMediaTypeNotSupportedException ex) {
		LOG.info("Unsupported media type", ex);
	}

	@ExceptionHandler(MicroServiceApplicationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorInfo handleApplicationException(MicroServiceApplicationException applicationException) {
		LOG.info("MicroServiceApplicationException: " + applicationException.getErrorInfo().toString(),
				applicationException);
		return applicationException.getErrorInfo();
	}

	@ExceptionHandler(InternalRestCallException.class)
	public void handleInternalRestCallException(InternalRestCallException internalRestCallException,
			HttpServletResponse response) throws IOException {

		LOG.info("InternalRestCallException: " + internalRestCallException.toString(), internalRestCallException);
		response.setStatus(internalRestCallException.getStatusCode());
		PrintWriter writer = response.getWriter();
		writer.write(internalRestCallException.getResponseBody());
		writer.flush();
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorInfo httpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
		LOG.info("httpMessageNotReadableException: " + httpMessageNotReadableException.toString(),
				httpMessageNotReadableException);

		String message = httpMessageNotReadableException.getMessage();
		if (httpMessageNotReadableException.getCause() instanceof JsonMappingException) {
			message = httpMessageNotReadableException.getMostSpecificCause().getMessage();

			JsonMappingException jsonMappingException = (JsonMappingException) httpMessageNotReadableException
					.getCause();
			List<FieldError> fieldErrors = new ArrayList<>();
			fieldErrors.add(new FieldError(jsonMappingException.getPathReference(), message));

			return new ValidationErrorInfo(ErrorCategory.VALIDATION_ERROR.name(), "validation", EMPTY_STRING,
					fieldErrors);
		}

		return new ErrorInfo(ErrorCategory.REQUEST_ERROR.name(), ErrorCategory.REQUEST_ERROR.name(), message);
	}

	@RequestMapping()
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorInfo handleUncaughtException(Exception ex) {
		ErrorInfo errorInfo = new ErrorInfo(ErrorCategory.UNHANDLED.name(), ErrorCategory.UNHANDLED.name(),
				ex.getMessage());
		LOG.error("Internal Server Error: " + errorInfo.toString(), ex);
		return errorInfo;
	}

	private List<FieldError> getFieldErrorsFromBindingResult(BindingResult bindingResult) {
		return bindingResult.getFieldErrors().stream()
				.map(fe -> new FieldError(fe.getField(), fe.getDefaultMessage()))
				.collect(Collectors.toList());
	}
}
