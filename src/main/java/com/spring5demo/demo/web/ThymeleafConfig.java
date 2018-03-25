package com.spring5demo.demo.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
public class ThymeleafConfig {
	
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCharacterEncoding("utf-8");
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.setTemplateEngineMessageSource(messageSource());
		templateEngine.addDialect(new SpringSecurityDialect());
		return templateEngine;
	}

	 @Bean
	 public ThymeleafViewResolver viewResolver() {
	 ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	 viewResolver.setTemplateEngine(templateEngine());
	 viewResolver.setCharacterEncoding("utf-8");
	 viewResolver.setOrder(0);
	 return viewResolver;
	 }

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:i18n/messages");
		messageSource.setDefaultEncoding("utf-8");
		return messageSource;
	}

	//
	@Bean
	public MessageSourceAccessor messages() {
		MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(messageSource(),
				LocaleContextHolder.getLocale());
		return messageSourceAccessor;
	}
}
