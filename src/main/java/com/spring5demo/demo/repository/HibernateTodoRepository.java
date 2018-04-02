package com.spring5demo.demo.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.spring5demo.demo.domain.Todo;

public class HibernateTodoRepository implements TodoRepository {

	private SessionFactory sessionFactory;

	public HibernateTodoRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<Todo> findAll() {
		CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
		CriteriaQuery<Todo> createQuery = criteriaBuilder.createQuery(Todo.class);
		createQuery.from(Todo.class);
		Query<Todo> queryTodo = currentSession().createQuery(createQuery);
		List<Todo> todoList = queryTodo.getResultList();
		return todoList;
		
//		return (List<Todo>) currentSession().createCriteria(Todo.class).list();
	}

	@Override
	public Todo findOneById(long id) {
		return (Todo) currentSession().get(Todo.class, id);
	}

	@Override
	public void remove(long id) {
		Todo beRemove = findOneById(id);
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

	@Override
	public List<Todo> findByOwner(String owner) {
		
		CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
		CriteriaQuery<Todo> criteriaQuery = criteriaBuilder.createQuery(Todo.class);
		Root<Todo> todoRoot = criteriaQuery.from(Todo.class);
		
		criteriaQuery.select(todoRoot).where(criteriaBuilder.equal(todoRoot.get("owner"), owner));
		
		List<Todo> todoList = currentSession().createQuery(criteriaQuery).getResultList();
		return todoList;

//		return (List<Todo>) currentSession().createCriteria(Todo.class).add(Restrictions.eq("owner", owner)).list();
	}

}
