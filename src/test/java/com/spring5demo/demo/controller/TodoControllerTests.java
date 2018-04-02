package com.spring5demo.demo.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.ui.ModelMap;

import com.spring5demo.demo.domain.Todo;
import com.spring5demo.demo.service.TodoService;

public class TodoControllerTests {
	
	private TodoService todoService;
	
	private TodoController todoController;
		
	@Before
	public void init() {
		this.todoService = Mockito.mock(TodoService.class);
		this.todoController = new TodoController(this.todoService);
	}

	@Test
	public void list() {
		// Setup
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
		
		Mockito.when(this.todoService.listTodos()).thenReturn(todoList);
		ModelMap model = new ModelMap();
		
		// Execute
		String viewName = 
				this.todoController.list(model);
		
		// test
		assertEquals(viewName, "todos");
		assertEquals(model.get("todos"), todoList);

		
	}
	
}
