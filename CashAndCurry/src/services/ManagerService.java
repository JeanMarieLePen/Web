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

import dao.ArtikalDAO;
import dao.CustomerDAO;
import dao.ManagerDAO;
import dtos.UpdateManagerDTO;
import model.Artikal;
import model.Customer;
import model.Manager;

@Path("/managers")
public class ManagerService {
	
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("managerDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("managerDAO", new ManagerDAO(contextPath));
		}
	}
	
	
	// proveriti
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Manager addNewManager(Manager manager) {
		System.out.println("UNOS MENADZERA U FAJL");
		ManagerDAO dao = (ManagerDAO)ctx.getAttribute("managerDAO");
		return dao.addNewManager(manager);
	}
	
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Manager getManager(@PathParam("username") String username){
		ManagerDAO dao = (ManagerDAO)ctx.getAttribute("managerDAO");
		return dao.findManager(username);
	}
	

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manager> getManagers(){
		ManagerDAO dao = (ManagerDAO)ctx.getAttribute("managerDAO");
		return dao.findAllManagers();
	}
	@GET
	@Path("/free/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manager> getFreeManagers(){
		ManagerDAO dao = (ManagerDAO)ctx.getAttribute("managerDAO");
		return dao.findFreeManagers();
	}
	@PUT
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Manager updateManager(UpdateManagerDTO manager) {
		ManagerDAO dao = (ManagerDAO)ctx.getAttribute("managerDAO");
		if(dao.checkPassword(manager.getOldPassword(), manager.getUsername()) == true) {
//			Manager m = new Manager(manager.isObrisan(), manager.getUsername(), manager.getPassword(), manager.getName(), manager.getLastname(), manager.getGender(), manager.getDateOfBirth(), manager.getRestaurant());
			Manager c = new Manager();
			c.setDateOfBirth(manager.getDateOfBirth()); c.setGender(manager.getGender()); c.setLastname(manager.getLastname()); c.setName(manager.getName());
			c.setPassword(manager.getPassword()); c.setUsername(manager.getUsername());
			
			
			return dao.updateManager(c);
		}
		return null;
	}
	
	@PUT
	@Path("/dodelaRestorana")
	@Produces(MediaType.APPLICATION_JSON)
	public Manager dodeliRestoran(Manager manager) {
		ManagerDAO dao = (ManagerDAO)ctx.getAttribute("managerDAO");
		return dao.updateManager(manager);
	}
}
