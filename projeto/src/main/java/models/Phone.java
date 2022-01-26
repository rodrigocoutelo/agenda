package models;

public class Phone {
	private Integer id;
	private Integer ddd;
	private Integer number;
	private PhoneType type;
	
	public Phone(Integer ddd, Integer number, PhoneType type ) {
		this.ddd = ddd;
		this.number = number;
		this.type = type;
	}
	
	public Phone(Integer id, Integer ddd, Integer number, PhoneType type ) {
		this.id = id;
		this.ddd = ddd;
		this.number = number;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDdd() {
		return ddd;
	}

	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public PhoneType getType() {
		return type;
	}

	public void setType(PhoneType type) {
		this.type = type;
	}

	public enum PhoneType {

		CELULAR("M", "Celular"), RESIDENCIAL("H", "Residencial"), COMERCIAL("W", "Comercial");

		private String value;
		private String label;

		PhoneType(String value, String label) {
			this.value = value;
			this.label = label;

		}

		public String getValue() {
			return this.value;
		}

		public String getLabel() {
			return this.label;
		}
		
		public static PhoneType getPhoneType(String value) {
			for (PhoneType pt : values()) {
				if (pt.value.equals(value)) {
					return pt;
				}
			}
			return null;
			
		}


	}

}
