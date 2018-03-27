package com.spring5demo.demo.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



@Entity
@Table(name = "users")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "{validate.user.notnull}")
	@Pattern(regexp = Constants.LOGIN_REGEX)
	@Size(min = 4, max = 50, message = "{validate.user.four2fifty}")
	@Column(length = 50, unique = true, nullable = false)
	private String username;

	@NotNull(message = "{validate.user.notnull}")
	@Size(min = 4, max = 60, message = "{validate.user.four2sixty}")
	@Column(length = 60)
	private String password;

	@Email(message = "{validate.user.email}")
	@Size(min = 5, max = 100)
	@Column(length = 100, unique = true)
	private String email;

	@NotNull(message = "{validate.user.notnull}")
	@Column(nullable = false)
	private boolean enabled = false;
	
	@Size(max = 20)
	@Column(name = "activation_key", length = 20)
	private String activationKey;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority", 
	           joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"), 
	           inverseJoinColumns = @JoinColumn(name = "authority_name", referencedColumnName = "name"))
	private Set<Authority> authorities = new HashSet<>();

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(Long id, String username, String email, boolean enabled, String activationKey) {
		this.id=id;
		this.username=username;
		this.email=email;
		this.enabled=enabled;
		this.activationKey=activationKey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public void addAuthority(Authority authority) {
		this.authorities.add(authority);
	}

	public String getActivationKey() {
		return activationKey;
	}

	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		User user = (User) o;

		return username.equals(user.username);
	}

	@Override
	public int hashCode() {
		return username.hashCode();
	}

	@Override
	public String toString() {
		return "User{" + "username='" + username + '\'' + ", email='" + email + '\'' + ", enabled='" + enabled + '\'' + ", activationKey='" + activationKey + '\'' + "}";
	}

}
