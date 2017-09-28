package com.physiccare.error;

public class SchemaAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -6969870208201618200L;

	public SchemaAlreadyExistsException(String message) {
		super(message);
	}
	
	public static class SchemaAlreadyExistsExceptionHandler {
		
	}
}
