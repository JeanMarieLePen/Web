package model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends GenericUser{
	private ArrayList<Order> listOfAllOrders = new ArrayList<Order>();
	private ArrayList<Order> listOfCompletedOrders = new ArrayList<Order>();
	private ArrayList<Order> listOfCanceledOrders = new ArrayList<Order>();
	//cart bi trebalo da bude funckionalnost fronta al ajd nek stoji za sada ovde
	private ArrayList<Artikal> cart;
	//broj otkazanih narudzbina
	private int numberOfCanceledOrders;
	//broj poena koje musterija ima a spram kojih ce mu se odredjivati kojoj kategoriji korisnika pripada(zlatni, srebrni, bronzani...) -> spram toga ostvaruje popust
	private int numberOfPoints;
	//tip musterije:
	private String typeOfCustomer;
	private ArrayList<Komentar> listOfCommentsMade = new ArrayList<Komentar>();
	
	public Customer() {
		super();
	}

	public Customer(ArrayList<Order> listOfAllOrders, ArrayList<Order> listOfCompletedOrders,
			ArrayList<Order> listOfCanceledOrders, ArrayList<Artikal> cart, int numberOfCanceledOrders,
			int numberOfPoints, String typeOfCustomer, ArrayList<Komentar> listOfCommentsMade) {
		super();
		this.listOfAllOrders = listOfAllOrders;
		this.listOfCompletedOrders = listOfCompletedOrders;
		this.listOfCanceledOrders = listOfCanceledOrders;
		this.cart = cart;
		this.numberOfCanceledOrders = numberOfCanceledOrders;
		this.numberOfPoints = numberOfPoints;
		this.typeOfCustomer = typeOfCustomer;
		this.listOfCommentsMade = listOfCommentsMade;
	}

	public ArrayList<Order> getListOfAllOrders() {
		return listOfAllOrders;
	}

	public void setListOfAllOrders(ArrayList<Order> listOfAllOrders) {
		this.listOfAllOrders = listOfAllOrders;
	}

	public ArrayList<Order> getListOfCompletedOrders() {
		return listOfCompletedOrders;
	}

	public void setListOfCompletedOrders(ArrayList<Order> listOfCompletedOrders) {
		this.listOfCompletedOrders = listOfCompletedOrders;
	}

	public ArrayList<Order> getListOfCanceledOrders() {
		return listOfCanceledOrders;
	}

	public void setListOfCanceledOrders(ArrayList<Order> listOfCanceledOrders) {
		this.listOfCanceledOrders = listOfCanceledOrders;
	}

	public ArrayList<Artikal> getCart() {
		return cart;
	}

	public void setCart(ArrayList<Artikal> cart) {
		this.cart = cart;
	}

	public int getNumberOfCanceledOrders() {
		return numberOfCanceledOrders;
	}

	public void setNumberOfCanceledOrders(int numberOfCanceledOrders) {
		this.numberOfCanceledOrders = numberOfCanceledOrders;
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

	public ArrayList<Komentar> getListOfCommentsMade() {
		return listOfCommentsMade;
	}

	public void setListOfCommentsMade(ArrayList<Komentar> listOfCommentsMade) {
		this.listOfCommentsMade = listOfCommentsMade;
	}
	
	
}
