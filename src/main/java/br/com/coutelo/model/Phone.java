package br.com.coutelo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "phone")
public class Phone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@Column(length = 2)
	private int ddd;

	@Column(length = 12)
	private int number;

	public enum PhoneType {
		CELULAR("Celular"), RESIDENCIAL("Residencial"), COMERCIAL("Comercial"), OUTRO("Outro");

		private String label;

		private PhoneType(final String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

	}

	@Enumerated(EnumType.STRING)
	private PhoneType phonetype;

	public Phone() {
		
	}
	public Phone(int ddd, int number, PhoneType phoneType) {
		this.ddd = ddd;
		this.number = number;
		this.phonetype = phoneType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getDdd() {
		return ddd;
	}

	public void setDdd(int ddd) {
		this.ddd = ddd;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public PhoneType getPhonetype() {
		return phonetype;
	}

	public void setPhonetype(PhoneType phonetype) {
		this.phonetype = phonetype;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phone other = (Phone) obj;
		if (ddd != other.ddd)
			return false;
		if (number != other.number)
			return false;
		if (phonetype != other.phonetype)
			return false;
		return true;
	}
	
	
}
