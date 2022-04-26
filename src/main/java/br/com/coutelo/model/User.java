package br.com.coutelo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Entity(name="User")
@Table(name="user")
@NamedQueries({
    @NamedQuery(name = "User.findByCredetials",
            query = "SELECT u FROM User u WHERE u.email = :email and u.password =: password"),
    @NamedQuery(name = "User.findByInitials",
    query = "SELECT u FROM User u WHERE u.name like CONCAT(:name,'%') order by u.name"),
    @NamedQuery(name = "User.findByEmail",
            query = "SELECT u FROM User u WHERE u.email = :email")})
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column
	private Long id;
	
	@Column
	@NotNull(message = "Nome não pode se nulo")
	private String name;
	
	@Column(unique = true)
	@NotNull(message = "E-mail não pode se nulo")
	@Email
	private String email;
	
	@Column
	@NotNull(message = "Password não pode se nulo")
	private String password;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Phone> phones = new ArrayList<Phone>();
	
	public enum Role {
		MASTER("MASTER"), ADMINISTRATOR("ADMINISTRATOR"), USER("USER");

		private String label;

		private Role(final String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}
	
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	public Role role;
	
	public User() {
		
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void addPhone(Phone phone) {
		this.phones.add(phone);
	}
	
	public void removePhone(Phone phone) {
		this.phones.remove(phone);
	}
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
