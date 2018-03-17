package com.spring5demo.demo.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class TodoSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    public TodoSecurityInitializer() {
        super(TodoSecurityConfig.class);
    }
}
