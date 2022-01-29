package model;

import java.util.ArrayList;

public class DeliveryMan extends GenericUser{

	
	private ArrayList<Order> listOfActiveOrders = new ArrayList<Order>();
	private ArrayList<Order> listOfAllOrders = new ArrayList<Order>();
	
	public DeliveryMan() {
		super();
	}
	public DeliveryMan(boolean obrisan, String username, String password, String name, String lastname, String gender,
			String dateOfBirth, ArrayList<Order> listOfActiveOrders, ArrayList<Order> listOfAllOrders) {
		super(obrisan, username, password, name, lastname, gender, dateOfBirth);
		this.listOfActiveOrders = listOfActiveOrders;
		this.listOfAllOrders = listOfAllOrders;
	}

	public ArrayList<Order> getListOfActiveOrders() {
		return listOfActiveOrders;
	}

	public void setListOfActiveOrders(ArrayList<Order> listOfActiveOrders) {
		this.listOfActiveOrders = listOfActiveOrders;
	}

	public ArrayList<Order> getListOfAllOrders() {
		return listOfAllOrders;
	}

	public void setListOfAllOrders(ArrayList<Order> listOfAllOrders) {
		this.listOfAllOrders = listOfAllOrders;
	}
	
	
}
