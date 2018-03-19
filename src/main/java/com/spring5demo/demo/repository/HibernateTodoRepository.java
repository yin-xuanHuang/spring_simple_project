package com.spring5demo.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.spring5demo.demo.domain.Todo;

public class HibernateTodoRepository implements TodoRepository {

	private SessionFactory sessionFactory;

	public HibernateTodoRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Todo> findAll() {
		return (List<Todo>) currentSession().createCriteria(Todo.class).list();
	}

	@Override
	public Todo findOne(long id) {
		return (Todo) currentSession().get(Todo.class, id);
	}

	@Override
	public void remove(long id) {
		Todo beRemove = findOne(id);
		if (beRemove != null) {
			currentSession().delete(beRemove);
		}
	}

	@Override
	public Todo save(Todo todo) {
		Serializable id = currentSession().save(todo);
		return new Todo((Long) id,
				todo.getOwner(),
				todo.getDescription(),
				todo.isCompleted());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Todo> findByOwner(String author) {
		return (List<Todo>) currentSession().createCriteria(Todo.class).add(Restrictions.eq("author", author)).list();
	}

}
