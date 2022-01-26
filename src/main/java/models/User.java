package models;

import java.util.ArrayList;

public class User {

	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String pass;
	private Role role;
	private ArrayList<Phone> phones = new ArrayList<Phone>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getName() {
		return firstName + " " + lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void addPhone(Phone phone) {
		this.phones.add(phone);
	}
	
	public void removePhone(Phone phone) {
		this.phones.remove(phone);
	}
	
	public ArrayList<Phone> getPhones() {
		return phones;
	}

	public void setPhones(ArrayList<Phone>  phones) {
		this.phones = phones;
	}

	public enum Role {

		USER("U", "Usuário"), ADMINISTRATOR("A", "Administrador");

		private String value;
		private String label;

		Role(String value, String label) {
			this.value = value;
			this.label = label;

		}

		public String getValue() {
			return this.value;
		}

		public String getLabel() {
			return this.label;
		}
		
		public static Role getRole(String value) {
			for (Role r : values()) {
				if (r.getValue().equals(value)) {
					return r;
				}
			}
			return null;
			
		}

	}

}
