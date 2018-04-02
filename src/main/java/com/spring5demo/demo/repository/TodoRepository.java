package com.spring5demo.demo.repository;

import java.util.List;

import com.spring5demo.demo.domain.Todo;

public interface TodoRepository {

    List<Todo> findAll();
    Todo findOneById(long id);
    
    void remove(long id);
    Todo save(Todo todo);

    List<Todo> findByOwner(String owner);

}
