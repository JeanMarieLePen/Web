package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import model.Order;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import dao.OrderDAO;

@Path("/orders")
public class OrderService {
	@Context
	ServletContext ctx;
	
	public OrderService(){	
	}
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("orderService") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("orderDAO", new OrderDAO(contextPath));
		}
	}
	
	
	//metoda koja dobavlja sve metode narudzbine koje je napravio jedan korisnik
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Order> getOrders(@PathParam("username") String username){
		OrderDAO dao = (OrderDAO)ctx.getAttribute("orderDAO");
		return dao.findAllOrders(username);
	}
	
	@GET
	@Path("/{username}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Order getOrder(@PathParam("username") String username, @PathParam("id") String id) {
		OrderDAO dao = (OrderDAO)ctx.getAttribute("orderDAO");
		return dao.findOrder(username, id);
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Order makeOrder(Order order) {
		OrderDAO dao = (OrderDAO)ctx.getAttribute("orderDAO");
		return dao.addNewOrder(order);
	}
	
}
