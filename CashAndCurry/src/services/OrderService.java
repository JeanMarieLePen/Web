package services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import model.Lokacija;
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
import dtos.SearchOrder;

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
	
	//metoda koja odobrava narudzbinu sa ID-om orderId za preuzimanje dostavljacu
	@GET
	@Path("/approveById/{orderId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Order approveById(@PathParam("orderId") int orderId) {
		OrderDAO dao = (OrderDAO)ctx.getAttribute("orderDAO");
		return dao.approveById(orderId);
	}
	
	//metoda koja odobrava narudzbinu sa ID-om orderId za pripremu u restoranu
		@GET
		@Path("/acceptById/{orderId}")
		@Produces(MediaType.APPLICATION_JSON)
		public Order acceptById(@PathParam("orderId") int orderId) {
			OrderDAO dao = (OrderDAO)ctx.getAttribute("orderDAO");
			return dao.acceptById(orderId);
		}
	
	//metoda koja vraca sve narudzbine koje je dostavljac preuzeo a nije ih dostavio
	@GET
	@Path("/notYetDelivered/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Order> getNotYetDelivered(@PathParam("username") String username){
		OrderDAO dao = (OrderDAO)ctx.getAttribute("orderDAO");
		return dao.findAllNotYetDelivered(username);
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
	public Collection<Order> getAllOrdersForRestaurant(@PathParam("name") String name){
		OrderDAO dao = (OrderDAO)ctx.getAttribute("orderDAO");
		return dao.findAllOrdersForRestaurant(name);
	}
	
	//metoda koja vraca sve narudzbine jednog dostavljaca
	@GET
	@Path("/deliveryMan/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Order> getAllOrdersForDeliveryMan(@PathParam("username") String username) {
		OrderDAO dao = (OrderDAO)ctx.getAttribute("orderDAO");
		return dao.findAllOrdersForDeliveryMan(username);
	}
	
	//metoda koja vraca sve narudzbine koje nisu preuzete ni od koga od dostavljaca
	@GET
	@Path("/noDeliveryMan")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Order> getAllOrdersWithNoDeliveryMan() {
		OrderDAO dao = (OrderDAO)ctx.getAttribute("orderDAO");
		return dao.findAllWithoutDeliveryMan();
	}
	
	//metoda koja vraca sve nedostavljene porudzbine za jednog korisnika
	@GET
	@Path("/notDelivered/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Order> getAllNotDeliveredForCustomer(@PathParam("id") String username) {
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
	
	@PUT
	@Path("/cancel/{orderId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Order cancel(@PathParam("orderId") int orderId) {
		OrderDAO dao = (OrderDAO)ctx.getAttribute("orderDAO");
		return dao.cancelOrder(orderId);
	}
	
	@GET
	@Path("/filtered/{request}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Order> getFilteredOrders(@PathParam("request") String request) throws ParseException{
		OrderDAO dao = (OrderDAO)ctx.getAttribute("orderDAO");
		String datumOd;
		String datumDo;
		int cenaOd;
		int cenaDo;
		String nazivRestorana;
		
		SearchOrder so = new SearchOrder();
		try {
			String[] tokens = request.split("&");
			for(String s : tokens) {
				String[] tempTokens = s.split(":");
				if(tempTokens[0].equals("datumOd")) {
					if(!tempTokens[1].equals("_")) {
						datumOd = tempTokens[1];
						so.setDatumOd(datumOd);
					}
				}
				else if(tempTokens[0].equals("datumDo")) {
					if(!tempTokens[1].equals("_")) {
						datumDo = tempTokens[1];
						so.setDatumDo(datumDo);
					}
					
				}
				else if(tempTokens[0].equals("cenaOd")) {
					if(!tempTokens[1].equals("_")) {
						cenaOd = Integer.parseInt(tempTokens[1]);
						so.setCenaOd(cenaOd);
					}
					
				}
				else if(tempTokens[0].equals("cenaDo")) {
					if(!tempTokens[1].equals("_")) {
						cenaDo = Integer.parseInt(tempTokens[1]);
						so.setCenaDo(cenaDo);
					}
				}
				else if(tempTokens[0].equals("nazivRestorana")) {
					if(!tempTokens[1].equals("_")) {
						nazivRestorana = tempTokens[1];
						so.setNazivRestorana(nazivRestorana);
					}
				}
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return dao.findFilteredResults(so);
	}
}
