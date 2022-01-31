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

import dao.AdministratorDAO;
import dao.CustomerDAO;
import dtos.UpdateAdminDTO;
import model.Administrator;
import model.Customer;


@Path("/administrators")
public class AdministratorService {
	
	@Context
	ServletContext ctx;
	
	public AdministratorService() {
		
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("administratorDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("administratorDAO", new AdministratorDAO(contextPath));
		}
	}	
	
//	@POST
//	@Path("/")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Administrator addAdministrator(Administrator administrator) {
//		AdministratorDAO dao = (AdministratorDAO)ctx.getAttribute("administratorDAO");
//		System.out.println("POGODAK SA FRONTA");
//		return dao.addNewAdministrator(administrator);
//	}
//	
//	@GET
//	@Path("/")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Collection<Administrator> getAdministrators(){
//		AdministratorDAO dao = (AdministratorDAO)ctx.getAttribute("administratorDAO");
//		return dao.findAllAdministrators();
//	}
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Administrator getAdministrator(@PathParam("username") String username) {
		AdministratorDAO dao = (AdministratorDAO)ctx.getAttribute("administratorDAO");
		return dao.findAdministrator(username);
	}
	
	@PUT
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Administrator updateAdministrator(UpdateAdminDTO administrator) {
		AdministratorDAO dao = (AdministratorDAO)ctx.getAttribute("administratorDAO");
		if(dao.checkPassword(administrator.getOldPassword(), administrator.getUsername()) == true) {
//			Administrator a = new Administrator(administrator.isObrisan(), administrator.getUsername(), administrator.getPassword(), administrator.getName(), administrator.getLastname(), administrator.getGender(), administrator.getDateOfBirth());
			Administrator c = new Administrator();
			c.setDateOfBirth(administrator.getDateOfBirth()); c.setGender(administrator.getGender()); c.setLastname(administrator.getLastname()); c.setName(administrator.getName());
			c.setPassword(administrator.getPassword()); c.setUsername(administrator.getUsername());
			return dao.updateAdministrator(c);
		}
		return null;
	}

}
