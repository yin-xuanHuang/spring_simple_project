package com.spring5demo.demo.service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.spring5demo.demo.domain.User;

@PropertySource("classpath:application.properties")
@Service("mailService")
public class MailServiceImpl implements MailService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JavaMailSender mailSender;

	@Value("${mail.dear:}")
	private String dear;

	@Value("${mail.content:}")
	private String content;

	@Value("${mail.subject:}")
	private String subject;

	@Value("${mail.from:}")
	private String mailFrom;

	@Value("${mail.link.part:}")
	private String linkPart;

	@Override
	public void sendEmail(Object object) {

		User user = (User) object;
		
		String link = linkPart + user.getActivationKey();

		MimeMessagePreparator preparator = getMessagePreparator(user, mailFrom, dear, content, subject, link);

		log.debug("dear={},content={},subject={},mailForm={},linkPart={}", dear, content, subject, mailFrom, linkPart);
		
		try {
			mailSender.send(preparator);
			log.debug("User({})'s email has been sent.", user.getUsername());
		} catch (MailException ex) {
			log.debug("User({})'s email not be sent.", user.getUsername());
		}
	}

	private MimeMessagePreparator getMessagePreparator(final User user, String mailFrom, String dear, String content,
			String subject, String link) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setFrom(mailFrom);
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
				mimeMessage.setText(dear + " " + user.getUsername() + ", " + content + " : " + link);
				mimeMessage.setSubject(subject);
			}
		};
		return preparator;
	}
}
