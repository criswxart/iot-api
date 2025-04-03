package com.tld.exception;

import com.tld.dto.ErrorDTO;

public class CustomDatabaseException extends RuntimeException {
    private final ErrorDTO errorDTO;

    public CustomDatabaseException(String message, Throwable cause) {
        super(message, cause);
        this.errorDTO = new ErrorDTO("DATABASE_ERROR", "Database operation failed", message);
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }
}
