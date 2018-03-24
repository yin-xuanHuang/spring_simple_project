package com.spring5demo.demo.service;

import java.util.List;

import com.spring5demo.demo.domain.Todo;

public interface TodoService {

    List<Todo> listTodos();
    void save(Todo todo);
    void complete(long id, String username);
    void remove(long id, String username);
    Todo findById(long id);
}
