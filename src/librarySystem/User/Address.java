package librarySystem.User;

public class Address {
	int number;
	String postcode;
	String city;
	
	public Address(int number, String postcode, String city) {
		this.number = number;
		this.postcode = postcode;
		this.city = city;
	}
	
	public String getPostcode() {
		return postcode;
	}

	@Override
	public String toString() {
		return this.number +" "+ this.postcode+" "+ this.city;
	}
	
	
}
