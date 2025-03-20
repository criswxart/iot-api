package com.tld.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<Object> handleDuplicateKeyException(DuplicateKeyException ex) {
		return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidJsonFormatException.class)
	public ResponseEntity<Object> handleInvalidJsonFormatException(InvalidJsonFormatException ex) {
		return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidApiKeyException.class)
	public ResponseEntity<Object> handleInvalidApiKeyException(InvalidApiKeyException ex) {
		return buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
		return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
	
	private ResponseEntity<Object> buildResponse(String message, HttpStatus status) {
		Map<String, Object> response = new HashMap();
		response.put("timestamp", LocalDateTime.now());
		response.put("status", status.value());
		response.put("error", status.getReasonPhrase());
		response.put("message", message);
		return new ResponseEntity<>(response, status);
	}
}
