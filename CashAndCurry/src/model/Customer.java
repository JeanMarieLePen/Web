package model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends GenericUser{
	private ArrayList<Order> listOfAllOrders = new ArrayList<Order>();
	private ArrayList<Artikal> cart;
	private int numberOfPoints;
	private TypeOfCustomer typeOfCustomer;
	
	public Customer() {
		
	}
	public Customer(ArrayList<Order> listOfAllOrders, ArrayList<Artikal> cart, int numberOfPoints,
			TypeOfCustomer typeOfCustomer) {
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
	public TypeOfCustomer getTypeOfCustomer() {
		return typeOfCustomer;
	}
	public void setTypeOfCustomer(TypeOfCustomer typeOfCustomer) {
		this.typeOfCustomer = typeOfCustomer;
	}
	
	
}
