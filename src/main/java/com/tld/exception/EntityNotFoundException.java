package com.tld.exception;

import com.tld.dto.ErrorDTO;

public class EntityNotFoundException extends RuntimeException {

    private ErrorDTO errorDTO;

    // Constructor sin mensaje ni causa
    public EntityNotFoundException() {
        super("Entity not found");
        this.errorDTO = new ErrorDTO("ENTITY_NOT_FOUND", "Entity not found", "The entity you requested could not be found.");
    }

    // Constructor con mensaje personalizado
    public EntityNotFoundException(String message) {
        super(message);
        this.errorDTO = new ErrorDTO("ENTITY_NOT_FOUND", message, "The entity you requested could not be found.");
    }

    // Constructor con mensaje, causa y ErrorDTO
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.errorDTO = new ErrorDTO("ENTITY_NOT_FOUND", message, "The entity you requested could not be found.");
    }

    // Constructor con causa y ErrorDTO
    public EntityNotFoundException(Throwable cause) {
        super(cause);
        this.errorDTO = new ErrorDTO("ENTITY_NOT_FOUND", "Entity not found", cause.getMessage());
    }

    // Getter para el ErrorDTO
    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }
}
