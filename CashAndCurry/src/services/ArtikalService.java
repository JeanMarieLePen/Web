package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.*;
import dao.ArtikalDAO;
import model.Artikal;

@Path("/artikli")
public class ArtikalService {

	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("artikalDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("artikalDAO", new ArtikalDAO(contextPath));
		}
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Artikal addArtikal(Artikal a) {
		ArtikalDAO dao = (ArtikalDAO)ctx.getAttribute("artikalDAO");
		return dao.save(a);
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Artikal> getArtikle(){
		ArtikalDAO dao = (ArtikalDAO)ctx.getAttribute("artikalDAO");
		return dao.findAllArtikli();
	}
	@GET
	@Path("/{nazivArtikla}")
	@Produces(MediaType.APPLICATION_JSON)
	public Artikal getArtikal(@PathParam("nazivArtikla") String naziv) {
		ArtikalDAO dao = (ArtikalDAO)ctx.getAttribute("artikalDAO");
		return dao.findArtikal(naziv);
	}
}
