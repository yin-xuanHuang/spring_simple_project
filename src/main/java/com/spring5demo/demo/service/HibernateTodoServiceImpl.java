package com.spring5demo.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring5demo.demo.domain.Todo;
import com.spring5demo.demo.repository.TodoRepository;

@Profile("orm")
@Service
@Transactional
public class HibernateTodoServiceImpl implements TodoService {

	private TodoRepository TodoRepository;
	
	@Autowired
	public HibernateTodoServiceImpl(TodoRepository TodoRepository){
		this.TodoRepository = TodoRepository;
	}
	
	
	@Override
	public List<Todo> listTodos() {
		return TodoRepository.findAll();
	}

	@Override
	public void save(Todo todo) {
		this.TodoRepository.save(todo);
	}

	@Override
	public void complete(long id) {
		Todo todo = this.findById(id);
		todo.setCompleted(true);
		this.TodoRepository.save(todo);
	}

	@Override
	public void remove(long id) {
		this.TodoRepository.remove(id);
	}

	@Override
	public Todo findById(long id) {
		return TodoRepository.findOne(id);
	}

}
