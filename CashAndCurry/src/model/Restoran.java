package model;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Restoran {
	
	private int id;
	private String name;
	private String type;
	private ArrayList<Artikal> menuItems = new ArrayList<Artikal>();
	private boolean opened;
	private Lokacija lokacija;
	private String logo;
//	private ArrayList<String> images = new ArrayList<String>();
	private ArrayList<Komentar> comments = new ArrayList<Komentar>();
	private String manager;
	private int ocena = 0;
	
	public Restoran() {
		super();
	}
	public Restoran(String name, String type, ArrayList<Artikal> menuItems, boolean opened, Lokacija lokacija,
			String logo, ArrayList<Komentar> comments, String manager, int ocena) {
		super();
		this.name = name;
		this.type = type;
		this.menuItems = menuItems;
		this.opened = opened;
		this.lokacija = lokacija;
		this.logo = logo;
		this.comments = comments;
		this.manager = manager;
		this.ocena = ocena;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ArrayList<Artikal> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(ArrayList<Artikal> menuItems) {
		this.menuItems = menuItems;
	}
	public boolean isOpened() {
		return opened;
	}
	public void setOpened(boolean opened) {
		this.opened = opened;
	}
	public Lokacija getLokacija() {
		return lokacija;
	}
	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public ArrayList<Komentar> getComments() {
		return comments;
	}
	public void setComments(ArrayList<Komentar> comments) {
		this.comments = comments;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public int getOcena() {
		return ocena;
	}
	public void setOcena(int ocena) {
		this.ocena = ocena;
	}
}
