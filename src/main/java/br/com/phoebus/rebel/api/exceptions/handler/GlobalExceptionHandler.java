package br.com.phoebus.rebel.api.exceptions.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.phoebus.rebel.api.exceptions.BlockedRebelException;
import br.com.phoebus.rebel.api.exceptions.ReportException;

/*
 * Copyright 2019 Phoebus Tecnologia Ltda.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * The message resource used to create the API response
	 */
	@Autowired
	private MessageSource messageSource;

	// ====== 400 ======

	/**
	 * Customize the response for MethodArgumentNotValidException
	 */
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ExceptionUtils.getStackTrace(ex));

		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getDefaultMessage());
		}

		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getDefaultMessage());
		}

		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}

	/**
	 * Customize the response for BindException
	 * 
	 */
	@Override
	public ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.info(ExceptionUtils.getStackTrace(ex));

		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getDefaultMessage());
		}

		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getDefaultMessage());
		}
		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}

	/**
	 * Customize the response for TypeMismatchException
	 * 
	 */
	@Override
	public ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.info(ExceptionUtils.getStackTrace(ex));

		final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type "
				+ ex.getRequiredType();

		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * Customize the response for MissingServletRequestPartException
	 */
	@Override
	public ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ExceptionUtils.getStackTrace(ex));

		final String error = ex.getRequestPartName() + " part is missing";
		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * Customize the response for MissingServletRequestParameterException
	 */
	@Override
	public ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ExceptionUtils.getStackTrace(ex));

		final String error = ex.getParameterName() + " parameter is missing";
		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * Customize the response for MethodArgumentTypeMismatchException
	 */
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
			final MethodArgumentTypeMismatchException ex, final WebRequest request) {
		logger.info(ExceptionUtils.getStackTrace(ex));

		final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * Customize the response for ConstraintViolationException
	 */
	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException ex,
			final WebRequest request) {
		logger.info(ExceptionUtils.getStackTrace(ex));

		final List<String> errors = new ArrayList<String>();
		for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}

		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * Customize the response for TransactionSystemException
	 */
	@ExceptionHandler({ TransactionSystemException.class })
	public ResponseEntity<Object> handleTransactionSystemException(TransactionSystemException ex) {
		logger.info(ExceptionUtils.getStackTrace(ex));

		String msgDev = null;
		Throwable t = ex.getCause();
		if (t instanceof ConstraintViolationException) {
			ConstraintViolationException cve = (ConstraintViolationException) t;
			Set<?> violations = cve.getConstraintViolations();
			ConstraintViolation<?> v = (ConstraintViolation<?>) violations.toArray()[0];
			msgDev = v.getPropertyPath() + " " + v.getMessage();
		} else {
			msgDev = ex.getMessage();
		}

		final String error = "Could not commit JPA transaction";

		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, msgDev, error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// ====== 404 ======

	/**
	 * Customize the response for NoHandlerFoundException
	 */
	@Override
	public ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ExceptionUtils.getStackTrace(ex));

		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

		final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// ====== 405 ======

	/**
	 * Customize the response for HttpRequestMethodNotSupportedException
	 */
	@Override
	public ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ExceptionUtils.getStackTrace(ex));

		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		final ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(),
				builder.toString());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// ====== 415 ======

	/**
	 * Customize the response for HttpMediaTypeNotSupportedException
	 */
	@Override
	public ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ExceptionUtils.getStackTrace(ex));

		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

		final ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(),
				builder.substring(0, builder.length() - 2));
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// ====== custom ======

	/**
	 * Customize the response for DataIntegrityViolationException
	 */
	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest request) {
		logger.info(ExceptionUtils.getStackTrace(ex));

		String userMessage = messageSource.getMessage("errors.resource.invalid.operation", null,
				LocaleContextHolder.getLocale());
		String developerMessage = ExceptionUtils.getRootCauseMessage(ex);

		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, developerMessage, userMessage);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * Customize the response for EmptyResultDataAccessException
	 */
	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
			WebRequest request) {
		logger.info(ExceptionUtils.getStackTrace(ex));

		String userMessage = messageSource.getMessage("errors.resource.not.found", null,
				LocaleContextHolder.getLocale());
		String developerMessage = ex.toString();

		final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, developerMessage, userMessage);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * Customize the response for JpaObjectRetrievalFailureException
	 */
	@ExceptionHandler({ JpaObjectRetrievalFailureException.class })
	public ResponseEntity<Object> handleJpaObjectRetrievalFailureException(JpaObjectRetrievalFailureException ex,
			WebRequest request) {
		logger.info(ExceptionUtils.getStackTrace(ex));

		String userMessage = messageSource.getMessage("errors.resource.not.found", null,
				LocaleContextHolder.getLocale());
		String developerMessage = ex.toString();

		final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, developerMessage, userMessage);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * Customize the response for BlockedRebelException
	 */
	@ExceptionHandler({ BlockedRebelException.class })
	public ResponseEntity<Object> handleBlockedRebelException(final BlockedRebelException ex,
			final WebRequest request) {
		logger.info(ExceptionUtils.getStackTrace(ex));

		String userMessage = "Rebel is blocked due to treason charges";
		String developerMessage = ex.toString();

		final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, developerMessage, userMessage);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * Customize the response for ReportException
	 */
	@ExceptionHandler({ ReportException.class })
	public ResponseEntity<Object> handleReportException(final ReportException ex, final WebRequest request) {
		logger.info(ExceptionUtils.getStackTrace(ex));

		String userMessage = "Failed to generate report";
		String developerMessage = ex.toString();

		final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, developerMessage, userMessage);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// ====== 500 ======

	/**
	 * Customize the response for Exception
	 */
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAllException(final Exception ex, final WebRequest request) {
		logger.info(ExceptionUtils.getStackTrace(ex));

		final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(),
				"error occurred");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

}
