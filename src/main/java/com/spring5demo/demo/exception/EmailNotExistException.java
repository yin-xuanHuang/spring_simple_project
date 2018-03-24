package com.spring5demo.demo.exception;

public class EmailNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailNotExistException(String message) {
		super(message);
	}
}
