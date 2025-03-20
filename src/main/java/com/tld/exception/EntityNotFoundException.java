package com.tld.exception;

public class EntityNotFoundException extends RuntimeException {

	private static String MESSAGE_INVALID_API_KEY = "No se encontró %s con %s: '%s";

	public EntityNotFoundException(String entity, String field, String value) {
		super(String.format(MESSAGE_INVALID_API_KEY, entity, field, value));
	}
}
