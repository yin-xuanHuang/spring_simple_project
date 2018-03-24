package com.spring5demo.demo.exception;

public class DuplicateEmailException extends RuntimeException {
	
    private static final long serialVersionUID = 1L;

	public DuplicateEmailException(String message) {
		super(message);
	}
}
