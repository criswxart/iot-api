package com.tld.dto;

public class ErrorDTO {

	 private String message;
	    private String details;

	    public ErrorDTO(String message, String details) {
	        this.message = message;
	        this.details = details;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public String getDetails() {
	        return details;
	    }
	
}
