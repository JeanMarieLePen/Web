package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dao.CustomerDAO;
import dao.DeliveryManDAO;
import dtos.UpdateDeliveryManDTO;
import model.Customer;
import model.DeliveryMan;

@Path("/deliverymen")
public class DeliveryManService {

	@Context
	ServletContext ctx;
	
	public DeliveryManService() {
		
	}
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("deliveryManDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("deliveryManDAO", new DeliveryManDAO(contextPath));
		}
	}
	
	//dodavanje novog dostavljaca u bazu
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public DeliveryMan addDeliveryMan(DeliveryMan deliveryMan) {
		DeliveryManDAO dao = (DeliveryManDAO)ctx.getAttribute("deliveryManDAO");
		return dao.addNewDeliveryMan(deliveryMan);
	}
	
	//dobavljanje svih dostavljaca iz baze
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<DeliveryMan> getDeliveryMen(){
		System.out.println("Nalazimo se u: " + ctx.toString());
		DeliveryManDAO dao = (DeliveryManDAO)ctx.getAttribute("deliveryManDAO");
		return dao.findAllDeliveryMen();
	}
	
	//dobavljanje konkretnog dobavljaca iz baze, identifikuje se preko usernamea
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public DeliveryMan getDeliveryMan(@PathParam("username") String username) {
		System.out.println("POGODAK SA FRONTA");
		DeliveryManDAO dao = (DeliveryManDAO)ctx.getAttribute("deliveryManDAO");
		return dao.findDeliveryMan(username);
	}
	@PUT
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public DeliveryMan updateDeliveryMan(UpdateDeliveryManDTO deliveryMan) {
		DeliveryManDAO dao = (DeliveryManDAO)ctx.getAttribute("deliveryManDAO");
		if(dao.checkPassword(deliveryMan.getOldPassword(), deliveryMan.getUsername()) == true) {
			DeliveryMan dm = new DeliveryMan(deliveryMan.isObrisan(), deliveryMan.getUsername(), deliveryMan.getPassword(), deliveryMan.getName(), deliveryMan.getLastname(), deliveryMan.getGender(), deliveryMan.getDateOfBirth(), deliveryMan.getListOfActiveOrders(), deliveryMan.getListOfAllOrders());
			return dao.updateDeliveryMan(dm);
		}
		return null;
	}
	
}
