package model;

public class Manager extends GenericUser {
	private String restaurant;

	public Manager(boolean obrisan, String username, String password, String name, String lastname, String gender,
			String dateOfBirth, String restaurant) {
		super(obrisan, username, password, name, lastname, gender, dateOfBirth);
		this.restaurant = restaurant;
	}

	public Manager() {
		super();
	}

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}
}
