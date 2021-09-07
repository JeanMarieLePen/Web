package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import model.Customer;
import model.Order;

public class OrderDAO {
	
	private Map<String, Order> orders = new HashMap<>();
	private Map<String, Customer> customers = new HashMap<>();
	private String contextPath;
	public OrderDAO() {
		
	}
	
	public OrderDAO(String contextPath) {
		this.contextPath = contextPath;
		loadOrders(contextPath);
	}
	
	public void loadOrders(String contextPath) {
		//ucitavamo sve Customere u mapu(svaki customer ima polje listaNarudzbina)
		try {
			JsonReader reader = new JsonReader(new FileReader(contextPath + "customers.json"));
			Gson gson = new Gson();
			Customer[] tempCustomers = gson.fromJson(reader, Customer[].class);
			for(Customer c : tempCustomers) {
				customers.put(c.getUsername(), c);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	//metoda koja pronalazi sve narudzbine jednog korisnika
	public Collection<Order> findAllOrders(String username){
		if(customers.containsKey(username)) {
			return (Collection<Order>)customers.get(username).getListOfAllOrders();
		}
		return null;
	}
	//metoda koja pronalazi konkretnu narudzbinu nekog korisnika
	public Order findOrder(String username, String idPorudzbine) {
		if(customers.containsKey(username)) {
			ArrayList<Order> tempLista = customers.get(username).getListOfAllOrders();
			for(Order o : tempLista) {
				if(o.getIdPorudzbine() == idPorudzbine) {
					return o;
				}
			}
		}
		return null;
	}
	
	//metoda koja cuva porudzbinu u bazu
	public Order addNewOrder(Order order) {
		if(customers.containsKey(order.getKupac().getUsername())){
			//dodajemo novu porudzbinu
			customers.get(order.getKupac().getUsername()).getListOfAllOrders().add(order);
			Gson gson = new Gson();
			String temp = gson.toJson(customers);
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "customers.json", true))){
				bw.append(temp);
				bw.append("\n");
				bw.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
			return order;
		}
		return null;
	}
	//metoda koja brise porudzbinu
	public Order removeOrder(Order order) {
		if(customers.containsKey(order.getKupac().getUsername())){
			//brisemo zeljenu porudzbinu
			customers.get(order.getKupac().getUsername()).getListOfAllOrders().remove(order);
			Gson gson = new Gson();
			String temp = gson.toJson(customers);
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "customers.json", true))){
				bw.append(temp);
				bw.append("\n");
				bw.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
			return order;
		}
		return null;
	}

}
