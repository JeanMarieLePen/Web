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

import model.Order;

public class OrderDAO {
	
	private Map<String, Order> orders = new HashMap<>();
	public OrderDAO() {
		
	}
	
	public OrderDAO(String contextPath) {
		loadOrders(contextPath);
	}
	
	public void loadOrders(String contextPath) {
		BufferedReader in = null;
		try {
			Gson gson = new Gson();
			File file = new File(contextPath + "orders.json");
			in = new BufferedReader(new FileReader(file));
			ArrayList<Order> listOfOrders = new ArrayList<Order>();
			String line;
			while((line = in.readLine()) != null) {
				Order o = gson.fromJson(line, Order.class);
				listOfOrders.add(o);
				orders.put(o.getIdPorudzbine(), o);
			}
			

		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if(in != null) {
				try {
					in.close();
				}
				catch(Exception e) {
					
				}
			}
		}
	}
	
	public Collection<Order> findAllOrders(){
		return orders.values();
	}
	
	public Order findOrder(String idPorudzbine) {
		return orders.containsKey(idPorudzbine) ? orders.get(idPorudzbine) : null;
	}
	
	public Order save(Order order) {
		orders.put(order.getIdPorudzbine(), order);
		Gson gson = new Gson();
		String temp = gson.toJson(order);
		try(BufferedWriter bw = new BufferedWriter(new FileWriter("orders.json", true))){
			System.out.println("Upis nove porudzbine u bazu.");
			bw.append(temp);
			bw.append("\n");
			bw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return order;
	}
	

}
