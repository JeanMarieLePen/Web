package model;

import sun.security.util.Password;

public class LoginUser {

	private String username;
	private String role;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public LoginUser(String username, String role) {
		super();
		this.username = username;
		this.role = role;
	}
	public LoginUser() {
		
	}
	
	
	
}
