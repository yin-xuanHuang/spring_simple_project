package com.spring5demo.demo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Todos {

	@XmlElement(name="todo")
	private List<Todo> todos = new ArrayList<Todo>();

	public Todos(List<Todo> todos) {
		this.todos = todos;
	}

	public Todos() {
	}

	public List<Todo> getTodos() {
		return todos;
	}

	public void setTodos(List<Todo> todos) {
		this.todos = todos;
	}
}
