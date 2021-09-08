package services;

import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import dao.RestoranDAO;
import model.Administrator;
import model.Customer;
import model.LoginUser;
import model.Manager;
import model.Restoran;

@Path("/login")
public class LoginService {
	private String contextPath;
	@Context
	ServletContext ctx;
	
	public LoginService() {
		
	}
	@PostConstruct
	public void init() {
		 this.contextPath = ctx.getRealPath("");
	}
	
	
	@POST
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@PathParam("username") String username,  String password){
		String tempRole = "";
		Map<String, Administrator> administrators = new HashMap<>();
		Map<String, Customer> customers = new HashMap<>();
		Map<String, Manager> managers = new HashMap<>();
		
		try {	
				JsonReader reader = new JsonReader(new FileReader(contextPath + "administrators.json"));
				Gson gson = new Gson();
				Administrator[] tempAdministrators = gson.fromJson(reader, Administrator[].class);
				for(Administrator c : tempAdministrators) {
					administrators.put(c.getUsername(), c);
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
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
			try {
				JsonReader reader = new JsonReader(new FileReader(contextPath + "managers.json"));
				Gson gson = new Gson();
				Manager[] tempManagers = gson.fromJson(reader, Manager[].class);
				if(tempManagers != null) {
					for(Manager c : tempManagers) {
						managers.put(c.getUsername(), c);
					}
				}

			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
			if(administrators.containsKey(username)) {
				tempRole = "admin";
				return tempRole;
			}
			if(customers.containsKey(username)) {
				tempRole = "customer";
				return tempRole;
			}
			if(managers.containsKey(username)) {
				tempRole = "manager";
				return tempRole;
			}
			return tempRole;
			
	}
	
}
