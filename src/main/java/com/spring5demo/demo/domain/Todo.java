package com.spring5demo.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="todo")
@XmlRootElement
public class Todo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String owner;

    @NotEmpty(message = "{validate.user.notnull}")
    private String description;

    private boolean completed = false;

    public Todo(long id, String owner, String description, boolean completed) {
		this.id = id;
		this.owner = owner;
		this.description = description;
		this.completed = completed;
	}

	public Todo() {
		
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {

        return String.format("Todo [id=%d, description=%s, owner=%s, completed=%b]%n",this.id, this.description, this.owner, this.completed);
    }
}
