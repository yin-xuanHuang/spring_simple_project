package com.spring5demo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring5demo.demo.domain.Todos;
import com.spring5demo.demo.exception.PermissionDeniedException;
import com.spring5demo.demo.exception.ResourceNotFoundException;
import com.spring5demo.demo.service.TodoService;

@Controller
@RequestMapping("/api")
public class TodoRestController {

    private TodoService todoService;

    @Autowired
    public TodoRestController(TodoService todoService) {
        this.todoService = todoService;
    }

    @RequestMapping(value="/todos")
    @ResponseBody
    public Todos getRestAllTodos(Model model) {

    	Todos todos = new Todos();
        todos.addTodos(todoService.listTodos());
        model.addAttribute("todos", todos);
        return todos;
    }
    
    @RequestMapping(value="/todos/{username}")
    @ResponseBody
    public Todos getRestUserTodos(@PathVariable("username") String username, Model model) {
    	
    	Todos todos = new Todos();
        todos.addTodos(todoService.findByOwner(username));
        model.addAttribute("todos", todos);
        return todos;
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
