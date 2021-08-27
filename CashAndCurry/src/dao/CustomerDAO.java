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
import java.util.StringTokenizer;
import sun.util.calendar.BaseCalendar.Date;
public class CustomerDAO {
	
	private Map<String, Customer> customers = new HashMap<>();
	public CustomerDAO() {
		
	}
	
	public CustomerDAO(String contextPath) {
		loadCustomers(contextPath);
	}
	
	public void loadCustomers(String contextPath) {
		BufferedReader in = null;
		try {
			Gson gson = new Gson();
			File file = new File(contextPath + "customers.json");
			in = new BufferedReader(new FileReader(file));
			ArrayList<Customer> listOfCustomers = new ArrayList<Customer>();
			String line;
			while((line = in.readLine()) != null) {
				Customer c = gson.fromJson(line, Customer.class);
				listOfCustomers.add(c);
				//dodajem i u mapu za svaki slucaj ako mapa bude zgodnija
				customers.put(c.getUsername(), c);
			}
			
/*			File file = new File(contextPath + "customers.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			
			while((line = in.readLine()) != null){
				line = line.trim();
				if(line.equals("") || line.indexOf("#") == 0) {
					continue;
				}
				st = new StringTokenizer(line, ";");
				while(st.hasMoreTokens()) {
					String username = st.nextToken().trim();
					String password = st.nextToken().trim();
					String firstName = st.nextToken().trim();
					String lastName = st.nextToken().trim();
					String gender = st.nextToken().trim();
					DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
					LocalDate dateOfBirth = LocalDate.parse(st.nextToken().trim(), format);
					
					
					
					customers.put(username, new Customer());
				}
			}*/
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
	//vraca sve musterije
	public Collection<Customer> findAllCustomers(){
		return customers.values();
	}
	//vraca musteriju sa zadatim korisnickim imenom
	public Customer findCustomer(String username) {
		return customers.containsKey(username) ? customers.get(username) : null;
	}
	//dodavanje musterije u mapu musterija
	public Customer save(Customer customer) {
		customers.put(customer.getUsername(), customer);
		Gson gson = new Gson();
		String temp = gson.toJson(customer);
		try(BufferedWriter bw = new BufferedWriter(new FileWriter("customers.json", true))){
			System.out.println("Upis novog korisnika u bazu.");
			bw.append(temp);
			bw.append("\n");
			bw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return customer;
	}
}
