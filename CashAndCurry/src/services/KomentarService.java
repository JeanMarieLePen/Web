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

import dao.KomentarDAO;
import model.Komentar;

@Path("/comments")
public class KomentarService {

	public KomentarService() {
		
	}
	
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("commentDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("commentDAO", new KomentarDAO(contextPath));
		}
	}
	
	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Komentar> getAllKomentari(@PathParam("name") String name){
		KomentarDAO dao = (KomentarDAO)ctx.getAttribute("commentDAO");
		return dao.findAllKomentare(name);
	}
	@GET
	@Path("/{name}/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Komentar> getKomentari(@PathParam("name") String name, @PathParam("username") String username){
		KomentarDAO dao = (KomentarDAO)ctx.getAttribute("commentDAO");
		return dao.findKomentar(username, name);
	}
	@POST
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Komentar addKomentar(@PathParam("name") String name, Komentar komentar) {
		KomentarDAO dao = (KomentarDAO)ctx.getAttribute("commentDAO");
		return dao.save(name, komentar);
	}

}