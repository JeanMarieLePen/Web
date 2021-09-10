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
import model.Lokacija;
import model.Manager;
import model.Restoran;

@Path("/restaurants")
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
	public Restoran addRestoran(Restoran r) {
		System.out.println("uslo se u POST RESTORAN");
		RestoranDAO dao = (RestoranDAO)ctx.getAttribute("restoranDAO");
		return dao.addNewRestoran(r);
	}
	
	@GET
	@Path("/hasManager/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Restoran> getRestaurantsWithManagers(){
		RestoranDAO dao = (RestoranDAO)ctx.getAttribute("restoranDAO");
		return dao.findAllRestaurantsWithManagers();
	}
	
	
	@GET
	@Path("/filtered/{request}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Restoran> getFilteredRestaurants(@PathParam("request") String request){
		RestoranDAO dao = (RestoranDAO)ctx.getAttribute("restoranDAO");
		String name = "";
		String lokacija = "";
		int ocena = 0;
		String tip = "";
		Restoran r = new Restoran();
		boolean tempBoolean = false;
		try {
			String[] tokens = request.split("&");
			for(String s : tokens) {
				String[] tempTokens = s.split(":");
				if(tempTokens[0].equals("name")) {
					if(!tempTokens[1].equals("_")) {
						name = tempTokens[1];
						r.setName(name);
						tempBoolean = true;
					}
				}
				else if(tempTokens[0].equals("type")) {
					if(!tempTokens[1].equals("_")) {
						tip = tempTokens[1];
						r.setType(tip);
						tempBoolean = true;
					}
					
				}
				else if(tempTokens[0].equals("lokacija")) {
					if(!tempTokens[1].equals("_")) {
						lokacija = tempTokens[1];
						Lokacija l = new Lokacija(lokacija);
						r.setLokacija(l);
						tempBoolean = true;
					}
					
				}
				else if(tempTokens[0].equals("ocena")) {
					if(!tempTokens[1].equals("_")) {
						ocena = Integer.parseInt(tempTokens[1]);
						r.setOcena(ocena);
						tempBoolean = true;
					}
				}
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("NAZIV TRAZENOG RESTORANA JE: " + r.getName());
		if(tempBoolean == true) {
			return dao.findFilteredResults(r);
		}
			return null;		
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
	@GET
	@Path("/noManager")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Restoran> getRestaurantsWithNoManagers(){
		RestoranDAO dao = (RestoranDAO)ctx.getAttribute("restoranDAO");
		return dao.findAllRestaurantsWithNoManagers();
	}
}
