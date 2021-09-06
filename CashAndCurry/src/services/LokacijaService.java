package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dao.LokacijaDAO;

@Path("/locations")
public class LokacijaService {

	
	@Context
	ServletContext ctx;
	public LokacijaService() {
		
	}
	
//	@PostConstruct
//	public void init() {
//		if(ctx.getAttribute("lokacijaDAO") == null) {
//			String contextPath = ctx.getRealPath("");
//			ctx.setAttribute("lokacijaDAO", new LokacijaDAO(contextPath));
//		}
//	}
//	@GET
//	@Path("/")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Collection<Lokacija> getLokacije(){
//		
//	}
}
