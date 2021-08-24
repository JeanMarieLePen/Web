package model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends GenericUser{
	private ArrayList<Order> listOfAllOrders = new ArrayList<Order>();
	private ArrayList<String> cart;
	private int numberOfPoints;
	private TypeOfCustomer typeOfCustomer;
	
	
}
