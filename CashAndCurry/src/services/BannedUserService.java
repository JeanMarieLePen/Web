package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dao.BannedUserDAO;
import dao.CustomerDAO;
import model.BannedUser;

@Path("/bannedUsers")
public class BannedUserService {

	
	@Context
	ServletContext ctx;
	
	public BannedUserService() {
		
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("bannedUserDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("bannedUserDAO", new BannedUserDAO(contextPath));
		}
	}
	
	@GET
	@Path("/ban/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public BannedUser banUser(@PathParam("username") String username) {
		BannedUserDAO dao = (BannedUserDAO)ctx.getAttribute("bannedUserDAO");
		return dao.banUser(username);
	}
	
	@GET
	@Path("/unban/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean unbanUser(@PathParam("username") String username) {
		BannedUserDAO dao = (BannedUserDAO)ctx.getAttribute("bannedUserDAO");
		return dao.unbanUser(username);
	}
	
}
