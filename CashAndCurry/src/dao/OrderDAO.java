package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import dtos.SearchOrder;
import model.Customer;
import model.Komentar;
import model.Order;
import model.Restoran;

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
	public Collection<Order> findAllOrders(String username){
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
//		//ponovno ucitavanje da bi osigurali da su ucitane novounete vrednosti
//		this.orders = null;
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
	public Collection<Order> findAllOrdersForRestaurant(String name){
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
	public Collection<Order> findAllUndeliveredForCustomer(String username){
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
	public Collection<Order> findAllWithoutDeliveryMan(){
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
	public Collection<Order> findAllOrdersForDeliveryMan(String username){
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

	public Order cancelOrder(int orderId) {
		// TODO Auto-generated method stub
//		this.orders = null;
//		loadOrders(contextPath);
		if(this.orders.containsKey(orderId)) {
			Order o = this.orders.get(orderId);
			o.setStatusPorudzbine("otkazana");
			
			Map<String, Customer> customers = new HashMap<>();
			try {
				JsonReader reader = new JsonReader(new FileReader(contextPath + "/customers.json"));
				Gson gson = new Gson();
				Customer[] tempCustomers = gson.fromJson(reader, Customer[].class);
				for(Customer c : tempCustomers) {
					customers.put(c.getUsername(), c);
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
			if(customers.containsKey(o.getIdKupca())) {
				Customer c = customers.get(o.getIdKupca());
				int temp = c.getNumberOfCanceledOrders();
				temp += 1;
				c.setNumberOfCanceledOrders(temp);
				
				
				Gson gson = new Gson();
//				String temp = gson.toJson(customers);
				
				Collection<Customer> tmp = customers.values();
				String fileInput = gson.toJson(tmp);
				
				try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "customers.json", false))){
					System.out.println("Izmena broja otkazanih porudzbina.");
					bw.append(fileInput);
					bw.append("\n");
					bw.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public Collection<Order> findAllNotYetDelivered(String username) {
		// TODO Auto-generated method stub
		if(this.orders.values().size() > 0) {
			ArrayList<Order> result = new ArrayList<Order>();
			for(Order o : this.orders.values()) {
				if(o.getIdDeliveryMana().equals(username) && !o.getStatusPorudzbine().equals("dostavljena")) {
					result.add(o);
				}
			}
			return result;
		}
		return null;
	}
	
	public Date convertDate(String datum) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH);
		Date result = formatter.parse(datum);
		return result;
	}
	public Collection<Order> findFilteredResults(SearchOrder so) throws ParseException{
		Collection<Order> test = new ArrayList<Order>();
		Predicate<Order> filter1; 
		Predicate<Order> filter2; 
		Predicate<Order> filter3;
		Predicate<Order> filter4;
		Predicate<Order> filter5;
		
		Date datumOd = convertDate(so.getDatumOd());
		Date datumDo = convertDate(so.getDatumDo());
		
		if(so.getCenaOd() == 0) {
			filter1 = e -> true;
		}else {
			filter1 = e -> e.getCena() >= so.getCenaOd();
		}
		if(so.getCenaDo() == 0) {
			filter2 = e -> true;
		}else {
			filter2 = e -> e.getCena() <= so.getCenaDo();
		}
		if(so.getDatumOd() == null) {
			filter3 = e -> true;
		}else {
			filter3 = e -> e.datumToDate().after(datumOd);
		}
		if(so.getDatumDo() == null) {
			filter4 = e -> true;
		}else {
			filter4 = e -> e.datumToDate().before(datumDo);
		}
		if(so.getNazivRestorana() == null) {
			filter5 = e -> true;
		}else {
			filter5 = e -> e.getIzKogRestorana().toLowerCase().equals(so.getNazivRestorana().toLowerCase());
		}
		
		test = orders.values().stream().filter(filter1).filter(filter2).filter(filter3).filter(filter4).filter(filter5).
				collect(Collectors.toList());
	
		Collection<Order> searchResult = new ArrayList<Order>();
		
		for(Order temp : test) {
			if(test.contains(temp)) {
				searchResult.add(temp);
			}
		}
	
		System.out.println("Pronadjeno je: " + searchResult.size() + " narudzbina koji zadovoljavaju kriterijume.");
		return searchResult;
	}

}
