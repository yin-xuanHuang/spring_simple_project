package com.spring5demo.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring5demo.demo.domain.Todo;
import com.spring5demo.demo.exception.PermissionDeniedException;
import com.spring5demo.demo.exception.ResourceNotFoundException;
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
	public void complete(long id, String username) {
		Todo todo = this.findOneById(id);
		if(todo == null) {
			throw new ResourceNotFoundException("Wrong request");
		}
		
		if(!todo.getOwner().equals(username)) {
			String message = "Permission denied.(owner: " + todo.getOwner() + ")";
			throw new PermissionDeniedException(message);
		}
		todo.setCompleted(true);
		this.todoRepository.save(todo);
	}

	@Override
	public void remove(long id, String username) {
		Todo todo = this.findOneById(id);
		if(!todo.getOwner().equals(username)) {
			String message = "Permission denied.(owner: " + todo.getOwner() + ")";
			throw new PermissionDeniedException(message);
		}
		this.todoRepository.remove(id);
	}

	@Override
	public Todo findOneById(long id) {
		return todoRepository.findOne(id);
	}


	@Override
	public List<Todo> findByOwner(String owner) {
		return this.todoRepository.findByOwner(owner);
	}
}
