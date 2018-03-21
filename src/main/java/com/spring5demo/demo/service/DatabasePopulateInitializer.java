package com.spring5demo.demo.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring5demo.demo.domain.AuthoritiesConstants;
import com.spring5demo.demo.domain.Authority;
import com.spring5demo.demo.domain.Todo;
import com.spring5demo.demo.domain.User;

@Component
public class DatabasePopulateInitializer {

    private TodoService messageBoardService;
    
    private UserService autoRegisterService;
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DatabasePopulateInitializer(TodoService messageBoardService, UserService autoRegisterService) {
        this.messageBoardService = messageBoardService;
        this.autoRegisterService = autoRegisterService;
    }

    @PostConstruct
    public void setup() {

        Todo todo = new Todo();
        todo.setOwner("marten@ya2do.io");
        todo.setDescription("Finish Spring Recipes - Security Chapter");

        messageBoardService.save(todo);

        todo = new Todo();
        todo.setOwner("marten@ya2do.io");
        todo.setDescription("Get Milk & Eggs");
        todo.setCompleted(true);
        messageBoardService.save(todo);

        todo = new Todo();
        todo.setOwner("marten@ya2do.io");
        todo.setDescription("Call parents.");

        messageBoardService.save(todo);

        todo = new Todo();
        todo.setOwner("jlong@pivotal.io");
        todo.setDescription("Prepare Cloud Native Presentation");

        messageBoardService.save(todo);

        todo = new Todo();
        todo.setOwner("rwinch@pivotal.io");
        todo.setDescription("Finish Spring Security Reactive.");

        messageBoardService.save(todo);
        
        autoRegister();

    }
    
    public void autoRegister() {
    	
    	this.autoRegisterService.saveAuthority(AuthoritiesConstants.ADMIN);
    	this.autoRegisterService.saveAuthority(AuthoritiesConstants.USER);
    	
    	User user = new User();
    	user.setUsername("adminx");
    	user.setPassword("secret");
    	user.setEmail("adminx@ya2do.io");
    	user.setEnabled(true);
    	Authority authority = this.autoRegisterService.findOneByName(AuthoritiesConstants.ADMIN);
    	
        user.addAuthority(authority);
        
        this.autoRegisterService.save(user);
        
        user = new User();
        user.setUsername("marten");
        user.setPassword("user");
        user.setEmail("marten@ya2do.io");
        user.setEnabled(true);
        
        this.autoRegisterService.save(user);
        
        user = new User();
        user.setUsername("jdoe");
        user.setPassword("unknown");
        user.setEmail("jdoe@ya2do.net");
        
        this.autoRegisterService.save(user);
        
    }
}
