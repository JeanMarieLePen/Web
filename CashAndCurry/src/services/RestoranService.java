package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dao.CustomerDAO;
import dao.RestoranDAO;
import model.Customer;
import model.Restoran;

@Path("/restorani")
public class RestoranService {
	
	@Context
	ServletContext ctx;
	
	public RestoranService() {
		
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("restoranDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("restoranDAO", new RestoranDAO(contextPath));
		}
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Restoran addRestoran(Restoran restoran) {
		RestoranDAO dao = (RestoranDAO)ctx.getAttribute("restoranDAO");
		return dao.addNewRestoran(restoran);
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Restoran> getRestorani(){
		RestoranDAO dao = (RestoranDAO)ctx.getAttribute("restoranDAO");
		return dao.findAllRestorani();
	}
	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Restoran getRestoran(@PathParam("name") String name) {
		RestoranDAO dao = (RestoranDAO)ctx.getAttribute("restoranDAO");
		return dao.findRestoran(name);
	}
	

}
