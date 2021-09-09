package model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends GenericUser{
	private ArrayList<Order> listOfAllOrders = new ArrayList<Order>();
	private ArrayList<Artikal> cart;
	private int numberOfPoints;
	private String typeOfCustomer;
	
	public Customer() {
		
	}

	public Customer(ArrayList<Order> listOfAllOrders, ArrayList<Artikal> cart, int numberOfPoints,
			String typeOfCustomer) {
		super();
		this.listOfAllOrders = listOfAllOrders;
		this.cart = cart;
		this.numberOfPoints = numberOfPoints;
		this.typeOfCustomer = typeOfCustomer;
	}

	public ArrayList<Order> getListOfAllOrders() {
		return listOfAllOrders;
	}

	public void setListOfAllOrders(ArrayList<Order> listOfAllOrders) {
		this.listOfAllOrders = listOfAllOrders;
	}

	public ArrayList<Artikal> getCart() {
		return cart;
	}

	public void setCart(ArrayList<Artikal> cart) {
		this.cart = cart;
	}

	public int getNumberOfPoints() {
		return numberOfPoints;
	}

	public void setNumberOfPoints(int numberOfPoints) {
		this.numberOfPoints = numberOfPoints;
	}

	public String getTypeOfCustomer() {
		return typeOfCustomer;
	}

	public void setTypeOfCustomer(String typeOfCustomer) {
		this.typeOfCustomer = typeOfCustomer;
	}
	
	
}
