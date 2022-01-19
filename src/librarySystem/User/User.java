package librarySystem.User;

public class User {
	private int userId;
	private String username;
	private String surname;
	private Address address;
	private String role;
	
	public User(int userId, String username, String surname, Address address, String role) {
		this.userId=userId;
		this.username=username;
		this.surname=surname;
		this.address=address;
		this.role=role;
	}

	public String getUsername() {
		return username;
	}

	public String getSurname() {
		return surname;
	}

	public Address getAddress() {
		return address;
	}

	public String getRole() {
		return role;
	}

	public int getUserId() {
		return userId;
	}
	public String getPostcode() {
		return address.getPostcode();
	}
}
