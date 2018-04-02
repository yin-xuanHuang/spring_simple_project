package com.spring5demo.demo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.spring5demo.demo.domain.Todo;
import com.spring5demo.demo.exception.PermissionDeniedException;
import com.spring5demo.demo.exception.TodoNotFoundException;
import com.spring5demo.demo.repository.TodoRepository;

public class TodoServiceImplMockTests {
	
	private TodoRepository todoRepository;
	
	private TodoService todoService;
	
	@Before
	public void init() {
		// mock for unit test
		todoRepository = mock(TodoRepository.class);
		todoService = new TodoServiceImpl(todoRepository);
	}
	
	// todoService.findOneById
	@Test
	public void serviceFindOneById() {
		// id, owner, description, complete
		Todo todo = new Todo(2, "Jane", "buy notebooks", false);
		when(todoRepository.findOneById(2)).thenReturn(todo);
		
		todoService.findOneById(2);
		
		verify(todoRepository, times(1)).findOneById(any(long.class));
	}
	
	// todoService.findByOwner
	@Test
	public void serviceFindByOwner() {
		// id, owner, description, complete
		Todo todo = new Todo(2, "Jane", "buy notebooks", false);
		ArrayList<Todo> todoList = new ArrayList<Todo>();
		todoList.add(todo);
		when(todoRepository.findByOwner("Jane")).thenReturn(todoList);
		
		todoService.findByOwner("Jane");
		
		verify(todoRepository, times(1)).findByOwner(any(String.class));
	}
	
	// todoService.save
	@Test
	public void serviceSave() {
		// id, owner, description, complete
		Todo todo = new Todo(2, "Jane", "buy notebooks", false);
		
		todoService.save(todo);
		
		verify(todoRepository, times(1)).save(todo);
	}
	
	// todoService.listTodos
	@Test
	public void serviceListTodos() {
		// id, owner, description, complete
		Todo buyNotebooks = new Todo(2, "Jane", "buy notebooks", true);
		Todo buyPens = new Todo(5, "Mike", "buy pens", false);
		Todo jogging = new Todo(8, "Marry", "jogging afternoon", false);
		Todo date = new Todo(12, "Joey", "have a date at 18:30", true);
		Todo meeting = new Todo(32, "Jael", "have a meeting at 10:00", false);
		
		ArrayList<Todo> todoList = new ArrayList<Todo>();
		todoList.add(buyNotebooks);
		todoList.add(buyPens);
		todoList.add(jogging);
		todoList.add(date);
		todoList.add(meeting);
		when(todoRepository.findAll()).thenReturn(todoList);
		
		todoService.listTodos();
		
		verify(todoRepository, times(1)).findAll();		
	}
	
	// todoService.remove
	@Test
	public void serviceRemoveSuccess() {
		// id, owner, description, complete
		Todo todo = new Todo(2, "Jane", "buy notebooks", false);
		when(todoRepository.findOneById(2)).thenReturn(todo);
		
		todoService.remove(2, "Jane");
		
		verify(todoRepository, times(1)).findOneById(2);
		verify(todoRepository, times(1)).remove(2);		
		
	}
	@Test(expected = PermissionDeniedException.class)
	public void serviceRemoveWithWrongOwner() {
		// id, owner, description, complete
		Todo todo = new Todo(2, "Jane", "buy notebooks", false);
		when(todoRepository.findOneById(2)).thenReturn(todo);
		
		todoService.remove(2, "Jack");
				
	}
	@Test(expected = TodoNotFoundException.class)
	public void serviceRemoveIdNotFound() {
		when(todoRepository.findOneById(2)).thenReturn(null);
		
		todoService.remove(2, "Jane");
				
	}
	
	// todoService.complete
	@Test
	public void serviceCompleteSuccess() {
		// id, owner, description, complete
		Todo todo = new Todo(2, "Jane", "buy notebooks", false);
		when(todoRepository.findOneById(2)).thenReturn(todo);
		
		todoService.complete(2, "Jane");
		
		verify(todoRepository, times(1)).findOneById(2);
		verify(todoRepository, times(1)).save(todo);
		
	}
	@Test(expected = PermissionDeniedException.class)
	public void serviceCompleteWithWrongOwner() {
		// id, owner, description, complete
		Todo todo = new Todo(2, "Jane", "buy notebooks", false);
		when(todoRepository.findOneById(2)).thenReturn(todo);
		
		todoService.complete(2, "Jack");
				
	}
	@Test(expected = TodoNotFoundException.class)
	public void serviceCompleteIdNotFound() {
		when(todoRepository.findOneById(2)).thenReturn(null);
		
		todoService.complete(2, "Jane");
				
	}
	
}
