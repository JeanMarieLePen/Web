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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import model.Customer;
import model.Komentar;
import model.Order;

public class OrderDAO {
	
	private Map<Integer, Order> orders = new HashMap<>();
//	private Map<String, Customer> customers = new HashMap<>();
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
			JsonReader reader = new JsonReader(new FileReader(contextPath + "orders.json"));
			Gson gson = new Gson();
			Order[] tempOrders = gson.fromJson(reader, Order[].class);
			for(Order o : tempOrders) {
				orders.put(o.getIdPorudzbine(), o);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	public int generateId() {
		int generatedId = 0;
		if(this.orders.values().size() > 0) {
			ArrayList<Order> listaOrders = (ArrayList<Order>) this.orders.values().stream().collect(Collectors.toList());
			List<Integer> listIds = new ArrayList<Integer>();
			for(Order o : listaOrders) {
				listIds.add(o.getIdPorudzbine());
			}
			
			for(int i = 0; i < listIds.size() + 1; i++) {
				if(!listIds.contains(i)) {
					generatedId = i;
					break;
				}
			}
		}
		return generatedId;
	}
	
	//metoda koja pronalazi sve narudzbine jednog korisnika
	public ArrayList<Order> findAllOrders(String username){
		ArrayList<Order> orderList = (ArrayList<Order>) this.orders.values().stream().collect(Collectors.toList());
		ArrayList<Order> result = new ArrayList<Order>();
		for(int i = 0; i < orderList.size(); i++) {
			if(orderList.get(i).getIdKupca().equals(username)) {
				result.add(orderList.get(i));
			}
		}
		return result;
	}
	//metoda koja pronalazi konkretnu narudzbinu nekog korisnika
	public Order findOrder(int idPorudzbine) {
		if(this.orders.containsKey(idPorudzbine)) {
			Order result = this.orders.get(idPorudzbine);
			return result;
		}
		return null;
	}
	
	//metoda koja cuva porudzbinu u bazu
	public Order addNewOrder(Order order) {
			int tempId = this.generateId();
			order.setIdPorudzbine(tempId);
			this.orders.put(tempId, order);
			
			Gson gson = new Gson();
			Collection<Order> tmp = this.orders.values();
			String fileInput = gson.toJson(tmp);
			
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "orders.json", false))){
				System.out.println("Upis nove porudzbine u bazu[fajl: orders.json].");
				bw.append(fileInput);
				bw.append("\n");
				bw.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
			return order;
	}
	//metoda koja menja postojecu narudzbinu
	public Order updateOrder(Order order) {
		//ponovno ucitavanje da bi osigurali da su ucitane novounete vrednosti
		this.orders = null;
		loadOrders(contextPath);
		
		if(this.orders.containsKey(order.getIdPorudzbine())) {
			this.orders.replace(order.getIdPorudzbine(), order);
			Gson gson = new Gson();
			String result = gson.toJson(this.orders.values());
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "orders.json"))){
				bw.append(result);
				bw.append("\n");
				bw.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
			return order;
		}
		return null;
	}
	
	//metoda koja pronalazi sve narudzbine jednog restorana
	public ArrayList<Order> findAllOrdersForRestaurant(String name){
		if(this.orders.values().size() > 0) {
			ArrayList<Order> result = new ArrayList<Order>();
			for(Order o : this.orders.values()) {
				if(o.getIzKogRestorana().equals(name)){
					result.add(o);
				}
			}
			return result;
		}
		return null;
	}
	
	//metoda koja pronalazi sve narudzbine koje nisu dostavljene a napravio ih je korisnik Username
	public ArrayList<Order> findAllUndeliveredForCustomer(String username){
		if(this.orders.values().size() > 0) {
			ArrayList<Order> result = new ArrayList<Order>();
			for(Order o : this.orders.values()) {
				if(o.getIdKupca().equals(username)){
					result.add(o);
				}
			}
			return result;
		}
		return null;
	}
	
	
	//metoda koja pronalazi sve narudzbine iz baze koje nije preuzeo ni jedan dostavljac
	public ArrayList<Order> findAllWithoutDeliveryMan(){
		if(this.orders.values().size() > 0) {
			ArrayList<Order> result = new ArrayList<Order>();
			for(Order o : this.orders.values()) {
				if(o.getStatusPorudzbine().equals("cekaDostavljaca")) {
					result.add(o);
				}
			}
			return result;
		}
		return null;
	}
	
	//metoda koja pronalazi sve narudzbine iz baze koje je preuzeo dostavljac(bilo ispunjene ili neispunjene)
	public ArrayList<Order> findAllOrdersForDeliveryMan(String username){
		if(this.orders.values().size() > 0) {
			ArrayList<Order> result = new ArrayList<Order>();
			for(Order o : this.orders.values()) {
				if(o.getIdDeliveryMana().equals(username)) {
					result.add(o);
				}
			}
			return result;
		}
		return null;
	}
	
	//metoda koja brise porudzbinu
	public Order removeOrder(Order order) {
		if(this.orders.containsKey(order.getIdPorudzbine())){
			//brisemo zeljenu porudzbinu
			this.orders.remove(order.getIdPorudzbine());
			Gson gson = new Gson();
			String temp = gson.toJson(this.orders.values());
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "orders.json", false))){
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
