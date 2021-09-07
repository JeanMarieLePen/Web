package dao;

import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import model.Customer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.DateFormat;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.util.StringTokenizer;
import sun.util.calendar.BaseCalendar.Date;
public class CustomerDAO {
	
	private Map<String, Customer> customers = new HashMap<>();
	private String contextPath;
	public CustomerDAO() {
		
	}
	
	public CustomerDAO(String contextPath) {
		this.contextPath = contextPath;
		loadCustomers(contextPath);
	}
	
	public void loadCustomers(String contextPath) {
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
	}
	//vraca sve musterije
	public Collection<Customer> findAllCustomers(){
		return customers.values();
	}
	//vraca musteriju sa zadatim korisnickim imenom
	public Customer findCustomer(String username) {
		return customers.containsKey(username) ? customers.get(username) : null;
	}
	
	//dodavanje novog Customera i njegovo cuvanje u bazu
	public Customer addNewCustomer(Customer customer) {
		if(!customers.containsKey(customer.getUsername())) {
			customers.put(customer.getUsername(), customer);
			Gson gson = new Gson();
			String temp = gson.toJson(customers);
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "customers.json", true))){
				System.out.println("Upis novog korisnika u bazu.");
				bw.append(temp);
				bw.append("\n");
				bw.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
			return customer;
		}	
		return null;
	}
	
	//izmena postojeceg korisnika i njegovo cuvanje u bazu
	public Customer updateCustomer(Customer customer) {
		if(customers.containsKey(customer.getUsername())) {
			customers.replace(customer.getUsername(), customer);
			Gson gson = new Gson();
			String temp = gson.toJson(customers);
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "customers.json", true))){
				bw.append(temp);
				bw.append("\n");
				bw.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
			return customer;
		}
		return null;
	}
	
	//brisanje korisnika iz baze
	public Customer removeCustomer(Customer customer) {
		if(customers.containsKey(customer.getUsername())) {
			customers.remove(customer.getUsername());
			Gson gson = new Gson();
			String temp = gson.toJson(customers);
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "customers.json", true))){
				bw.append(temp);
				bw.append("\n");
				bw.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
			return customer;
		}
		return null;
	}
	
}
