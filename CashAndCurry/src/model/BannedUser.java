package model;

public class BannedUser {
	private String username;
	private int brojDana;
	
	public BannedUser() {
		super();
	}
	public BannedUser(String username, int brojDana) {
		super();
		this.username = username;
		this.brojDana = brojDana;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getBrojDana() {
		return brojDana;
	}
	public void setBrojDana(int brojDana) {
		this.brojDana = brojDana;
	}
}
