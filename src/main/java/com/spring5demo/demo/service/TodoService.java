package com.spring5demo.demo.service;

import java.util.List;

import com.spring5demo.demo.domain.Todo;

public interface TodoService {

	Todo findOneById(long id);
	
    void save(Todo todo);
    void complete(long id, String username);
    void remove(long id, String username);
    
    List<Todo> findByOwner(String owner);
    List<Todo> listTodos();
}
