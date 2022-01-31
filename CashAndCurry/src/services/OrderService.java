package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import model.Order;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
	
	
	//metoda koja dobavlja sve narudzbine koje je napravio jedan korisnik
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Order> getOrders(@PathParam("username") String username){
		OrderDAO dao = (OrderDAO)ctx.getAttribute("orderDAO");
		return dao.findAllOrders(username);
	}
	
	//metoda koja dobavlja konkretnu narudzbinu koja ima id = {id}
	@GET
	@Path("/byOrderId/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Order getOrder(@PathParam("id") int id) {
		OrderDAO dao = (OrderDAO)ctx.getAttribute("orderDAO");
		return dao.findOrder(id);
	}
	
	//metoda koja vraca sve narudzbine za jedan restoran[MANAGER]
	@GET
	@Path("/byRestaurantName/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Order> getAllOrdersForRestaurant(@PathParam("name") String name){
		OrderDAO dao = (OrderDAO)ctx.getAttribute("orderDAO");
		return dao.findAllOrdersForRestaurant(name);
	}
	
	//metoda koja vraca sve narudzbine jednog dostavljaca
	@GET
	@Path("/deliveryMan/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Order> getAllOrdersForDeliveryMan(@PathParam("username") String username) {
		OrderDAO dao = (OrderDAO)ctx.getAttribute("orderDAO");
		return dao.findAllOrdersForDeliveryMan(username);
	}
	
	//metoda koja vraca sve narudzbine koje nisu preuzete ni od koga od dostavljaca
	@GET
	@Path("/noDeliveryMan")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Order> getAllOrdersWithNoDeliveryMan() {
		OrderDAO dao = (OrderDAO)ctx.getAttribute("orderDAO");
		return dao.findAllWithoutDeliveryMan();
	}
	
	//metoda koja vraca sve nedostavljene porudzbine za jednog korisnika
	@GET
	@Path("/notDelivered/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Order> getAllNotDeliveredForCustomer(@PathParam("id") String username) {
		OrderDAO dao = (OrderDAO)ctx.getAttribute("orderDAO");
		return dao.findAllUndeliveredForCustomer(username);
	}
	
	
	//metoda koja kreira novu narudzbinu i upisuje je u bazu
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Order makeOrder(Order order) {
		OrderDAO dao = (OrderDAO)ctx.getAttribute("orderDAO");
		return dao.addNewOrder(order);
	}
	
	//metoda koja menja postojecu narudzbinu, npr kad korisnik hoce da otkaze nedostavljenu narudzbinu
	//ili kada dostavljac zeli da je preuzme na sebe
	@PUT
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Order updateOrder(Order order) {
		OrderDAO dao = (OrderDAO)ctx.getAttribute("orderDAO");
		return dao.updateOrder(order);
	}
}
