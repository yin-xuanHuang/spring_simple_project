package com.spring5demo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring5demo.demo.domain.MyError;
import com.spring5demo.demo.domain.Todos;
import com.spring5demo.demo.exception.PermissionDeniedException;
import com.spring5demo.demo.exception.ResourceNotFoundException;
import com.spring5demo.demo.service.TodoService;

@RestController
@RequestMapping("/api")
public class TodoRestController {

    private TodoService todoService;

    @Autowired
    public TodoRestController(TodoService todoService) {
        this.todoService = todoService;
    }

	@RequestMapping(value="/todos")
    public ResponseEntity<?> getRestAllTodos(Model model) {

    	Todos todos = new Todos(todoService.listTodos());
        
        if(todos.getTodos().isEmpty()) {
        	MyError error = new MyError(404, "Todos not found");
        	return new ResponseEntity<MyError>(error, HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<Todos>(todos, HttpStatus.OK);

    }
    
	@RequestMapping(value="/todos/{username}")
    public ResponseEntity<?> getRestUserTodos(@PathVariable("username") String username, Model model) {
    	
        Todos todos = new Todos(todoService.findByOwner(username));
        
        if(todos.getTodos().isEmpty()) {
        	MyError error = new MyError(404, "Todos [" + username + "] not found");
        	return new ResponseEntity<MyError>(error, HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<Todos>(todos, HttpStatus.OK);
    }
    
    @ExceptionHandler(PermissionDeniedException.class)
    public String handlePermissionDenied() {
    	return "errors/permissionDenied";
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFound() {
    	return "errors/notFound";
    }

}
