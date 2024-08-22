package com.namrata.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name="users")
@ToString(onlyExplicitlyIncluded = true)
public class User {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotEmpty
	@Column(nullable = false)
	@ToString.Include
	private String firstName;

	@ToString.Include
	private String lastName;

	@NotEmpty
	@Column(nullable = false, unique = true)
	@Email(message = "{errors.invalid_email}")
	@ToString.Include
	private String email;

	@ToString.Exclude
	private String password;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(
		name = "user_role",
		joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") },
		inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
	)
	@ToString.Include
	private List<Role> roles;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
    private List<Order> orders;
	
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
	@ToString.Exclude
	private List<CartItem> cartItems;

	public User() {}

	public User(User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.roles = user.getRoles();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
