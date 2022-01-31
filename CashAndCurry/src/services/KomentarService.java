package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
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
	
	//pregled svih ODOBRENIH komentara ostavljenih na jedan restoran
	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Komentar> getAllOdobreniKomentari(@PathParam("name") String name){
		KomentarDAO dao = (KomentarDAO)ctx.getAttribute("commentDAO");
		return dao.findAllKomentare(name);
	}
	
	//dobavljanje svih komentara koje Kupac treba da vidi
	@GET
	@Path("/byRestoran/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Komentar> getAllOdobreniKomentariForCustomer(@PathParam("name") String name){
		KomentarDAO dao = (KomentarDAO)ctx.getAttribute("commentDAO");
		return dao.findAllKomentareForCustomer(name);
	}
	
	
	//pregled svih NEODOBRENIH komentara ostavljenih na jedan restoran
	@GET
	@Path("/unmoderated/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Komentar> getAllNeodobreniKomentari(@PathParam("name") String name){
		KomentarDAO dao = (KomentarDAO)ctx.getAttribute("commentDAO");
		return dao.findAllUnmoderatedComments(name);
	}
	
	@POST
	@Path("/moderated/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Komentar> sendModeratedComments(@PathParam("name") String name, ArrayList<Komentar> listaKomentara){
		KomentarDAO dao = (KomentarDAO)ctx.getAttribute("commentDAO");
		return dao.updateModeratedComments(name, listaKomentara);
	}
	
	
	
	
	
	//pregled komentara koje je napravio jedan korisnik
	@GET
	@Path("/{name}/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Komentar> getKomentari(@PathParam("name") String name, @PathParam("username") String username){
		KomentarDAO dao = (KomentarDAO)ctx.getAttribute("commentDAO");
		return dao.findKomentar(username, name);
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Komentar addKomentar(Komentar komentar) {
		KomentarDAO dao = (KomentarDAO)ctx.getAttribute("commentDAO");
		return dao.save(komentar);
	}
	
	@DELETE
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Komentar removeKomentarById(Komentar komentar) {
		KomentarDAO dao = (KomentarDAO)ctx.getAttribute("commentDAO");
		return dao.removeComment(komentar);
	}
	

}