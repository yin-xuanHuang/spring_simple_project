package com.spring5demo.demo.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.spring5demo.demo.domain.AuthoritiesConstants;
import com.spring5demo.demo.domain.Authority;
import com.spring5demo.demo.domain.Todo;
import com.spring5demo.demo.domain.User;

@Profile("populate")
@Component
public class DatabasePopulateInitializer {

	private TodoService todoService;

	private AuthorityService authorityService;

	private UserService userService;

	@Autowired
	public DatabasePopulateInitializer(TodoService todoService, UserService userService,
			AuthorityService authorityService) {
		this.todoService = todoService;
		this.authorityService = authorityService;
		this.userService = userService;
	}

	@PostConstruct
	public void setup() {

		Todo todo = new Todo();
		todo.setOwner("marten");
		todo.setDescription("Finish Spring Recipes - Security Chapter");

		todoService.save(todo);

		todo = new Todo();
		todo.setOwner("marten");
		todo.setDescription("Get Milk & Eggs");
		todo.setCompleted(true);
		todoService.save(todo);

		todo = new Todo();
		todo.setOwner("marten");
		todo.setDescription("Call parents.");

		todoService.save(todo);

		todo = new Todo();
		todo.setOwner("jlong");
		todo.setDescription("Prepare Cloud Native Presentation");

		todoService.save(todo);

		todo = new Todo();
		todo.setOwner("rwinch");
		todo.setDescription("Finish Spring Security Reactive.");

		todoService.save(todo);

		autoRegister();

	}

	public void autoRegister() {

		this.authorityService.save(AuthoritiesConstants.ADMIN);
		this.authorityService.save(AuthoritiesConstants.USER);
		
		Authority authorityAdmin = this.authorityService.findOneByName(AuthoritiesConstants.ADMIN);
		Authority authorityUser = this.authorityService.findOneByName(AuthoritiesConstants.USER);

		User user = new User();
		user.setUsername("adminx");
		user.setPassword("secret");
		user.setEmail("adminx@ya2io.io");
		user.setEnabled(true);
		user.addAuthority(authorityAdmin);
		user.addAuthority(authorityUser);

		this.userService.save(user);

		user = new User();
		user.setUsername("marten");
		user.setPassword("user");
		user.setEmail("marten@ya2io.io");
		user.setEnabled(true);
		user.addAuthority(authorityUser);

		this.userService.save(user);

		user = new User();
		user.setUsername("jdoe");
		user.setPassword("unknown");
		user.setEmail("jdoe@ya2do.net");
		user.addAuthority(authorityUser);

		this.userService.save(user);

	}
}
