package com.pme.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionsHandler {
	private final ErrorModel errorModel;

	@Autowired
	public ExceptionsHandler(ErrorModel errorModel) {
		this.errorModel = errorModel;
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorModel> handleHttpMessageNotReadableException (HttpMessageNotReadableException ex) {
		return ResponseEntity.status(400).body(new ErrorModel("BAD Json Request"));
	}


	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorModel> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		errorModel.setErrors(errors);
		return ResponseEntity.status(400).body(errorModel);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorModel> handleNoSuchElementException(NoSuchElementException ex) {
		return ResponseEntity.status(404).body(new ErrorModel("Data NOT Found"));
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorModel> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
		return ResponseEntity.status(405).body(new ErrorModel(ex.getMessage()));
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<ErrorModel> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		return ResponseEntity.status(404).body(new ErrorModel("Data does NOT Exists"));
	}

	@ExceptionHandler(ConnectException.class)
	public ResponseEntity<ErrorModel> handleConnectException(ConnectException ex) {
		return ResponseEntity.status(502).body(new ErrorModel("Service NOT Available"));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorModel> handleGenericException(Exception ex) {
		return ResponseEntity.status(500).body(new ErrorModel(ex.getMessage()));
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorModel> handleUsernameNotFoundException(UsernameNotFoundException ex) {
		return ResponseEntity.status(404).body(new ErrorModel(ex.getMessage()));
	}
}