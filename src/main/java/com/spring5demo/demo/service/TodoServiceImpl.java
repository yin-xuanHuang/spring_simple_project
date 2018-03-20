package com.spring5demo.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring5demo.demo.domain.Todo;
import com.spring5demo.demo.repository.TodoRepository;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {

	private TodoRepository todoRepository;
	
	@Autowired
	public TodoServiceImpl(TodoRepository todoRepository){
		this.todoRepository = todoRepository;
	}
	
	
	@Override
	public List<Todo> listTodos() {
		return todoRepository.findAll();
	}

	@Override
	public void save(Todo todo) {
		this.todoRepository.save(todo);
	}

	@Override
	public void complete(long id) {
		Todo todo = this.findById(id);
		todo.setCompleted(true);
		this.todoRepository.save(todo);
	}

	@Override
	public void remove(long id) {
		this.todoRepository.remove(id);
	}

	@Override
	public Todo findById(long id) {
		return todoRepository.findOne(id);
	}

}
