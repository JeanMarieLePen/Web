package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;

import dao.CustomerDAO;
import model.Customer;


@Path("/customers")
public class CustomerService {

	
	@Context
	ServletContext ctx;
	
	public CustomerService() {
		
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("customerDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("customerDAO", new CustomerDAO(contextPath));
		}
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{name}")
	public String getGreeting(@PathParam("name") String name) {
		String greeting = "Hello " + name;
		return greeting;
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer addCustomer(Customer customer) {
		CustomerDAO dao = (CustomerDAO)ctx.getAttribute("customerDAO");
		System.out.println("POGODAK SA FRONTA");
		return dao.addNewCustomer(customer);
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> getCustomers(){
		CustomerDAO dao = (CustomerDAO)ctx.getAttribute("customerDAO");
		return dao.findAllCustomers();
	}
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@PathParam("username") String username) {
		CustomerDAO dao = (CustomerDAO)ctx.getAttribute("customerDAO");
		return dao.findCustomer(username);
	}
	
}
