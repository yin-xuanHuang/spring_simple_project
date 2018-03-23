package com.spring5demo.demo.service;

public interface MailService {
	public void sendConfirmationEmail(Object object);
	public void sendPasswordResetEmail(Object object, String temp);
}
