package model;

public class Manager extends GenericUser {
	private String restaurant;

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}

	public Manager(String restaurant) {
		super();
		this.restaurant = restaurant;
	}
	
}
