package com.spring5demo.demo.web;

import java.util.Locale;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")
public class WebConfig implements WebMvcConfigurer {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${spring.mail.host:}")
	private String mailHost;

	@Value("${spring.mail.username:}")
	private String username;

	@Value("${spring.mail.password:}")
	private String password;

	@Value("${spring.mail.properties.mail.smtp.socketFactory.port:0}")
	private int port;

	@Value("${spring.mail.properties.mail.smtp.starttls.enable:}")
	private String starttlsEnable;

	@Value("${spring.mail.properties.mail.smtp.auth:}")
	private String smtpAuth;

	@Value("${spring.mail.properties.mail.transport.protocol:}")
	private String protocol;

	@Value("${spring.mail.properties.mail.debug:}")
	private String debug;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("redirect:/todos");
	}
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
	
	@Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }
	
	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setCookieName("language");
        cookieLocaleResolver.setCookieMaxAge(3600);
        cookieLocaleResolver.setDefaultLocale(new Locale("en"));
        return cookieLocaleResolver;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Bean
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		log.debug("mailHost={},port={},username={},password={},enable={},smtpAuth={},protocol={},debut={}", mailHost,
				port, username, password, starttlsEnable, smtpAuth, protocol, debug);

		mailSender.setHost(mailHost);
		mailSender.setPort(port);
		mailSender.setUsername(username);
		mailSender.setPassword(password);

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable", starttlsEnable);
		javaMailProperties.put("mail.smtp.auth", smtpAuth);
		javaMailProperties.put("mail.transport.protocol", protocol);
		javaMailProperties.put("mail.debug", debug);

		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}

}
