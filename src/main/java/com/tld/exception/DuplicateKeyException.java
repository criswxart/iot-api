package com.tld.exception;

public class DuplicateKeyException extends RuntimeException {
	
	private static final String DUPLICATE_KEY = "No se puede ingresar un registro duplicado en %s. El valor '%s' ya existe.";
	
    public DuplicateKeyException(String entity, String field) {
        super(String.format(DUPLICATE_KEY,entity, field));
    }
}