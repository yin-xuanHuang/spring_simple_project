package com.spring5demo.demo.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring5demo.demo.domain.Todo;
import com.spring5demo.demo.exception.PermissionDeniedException;
import com.spring5demo.demo.service.TodoService;

@Controller
@RequestMapping("/todos")
public class TodoController {

    private TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public String list(Model model) {
        List<Todo> todos = todoService.listTodos();
        model.addAttribute("todos", todos);
        return "todos";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("todo", new Todo());
        return "todo-create";
    }

    @PostMapping
    public String newMessage(@ModelAttribute @Valid Todo todo, BindingResult errors, Principal principal, Model model) {

        if (errors.hasErrors()) {
        	model.addAttribute("message", "Can not be empty.");
            return "todo-create";
        }
        todo.setOwner(principal.getName());
        todoService.save(todo);
        return "redirect:/todos";
    }

    @PutMapping("/{todoId}/completed")
    public String complete(@PathVariable("todoId") long todoId, Principal principal) {
        this.todoService.complete(todoId, principal.getName());
        return "redirect:/todos";
    }


    @DeleteMapping("/{todoId}")
    public String delete(@PathVariable("todoId") long todoId, Principal principal) {
        this.todoService.remove(todoId, principal.getName());
        return "redirect:/todos";
    }
    
    @ExceptionHandler(PermissionDeniedException.class)
    public String handlePermissionDenied() {
    	return "errors/permissionDenied";
    }

}
