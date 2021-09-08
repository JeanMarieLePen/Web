package model;
import java.util.ArrayList;

public class Restoran {
	
	private String name;
	private String type;
	private ArrayList<Artikal> menuItems = new ArrayList<Artikal>();
	private boolean opened;
	private Lokacija lokacija;
	private ArrayList<String> images = new ArrayList<String>();
	private ArrayList<Komentar> comments = new ArrayList<Komentar>();
	private Manager manager;
	private int ocena;
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
	public ArrayList<String> getImages() {
		return images;
	}
	public void setImages(ArrayList<String> images) {
		this.images = images;
	}
	public ArrayList<Komentar> getComments() {
		return comments;
	}
	public void setComments(ArrayList<Komentar> comments) {
		this.comments = comments;
	}
	public Manager getManager() {
		return manager;
	}
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	public int getOcena() {
		return ocena;
	}
	public void setOcena(int ocena) {
		this.ocena = ocena;
	}
	public Restoran(String name, String type, ArrayList<Artikal> menuItems, boolean opened, Lokacija lokacija,
			ArrayList<String> images, ArrayList<Komentar> comments, Manager manager, int ocena) {
		super();
		this.name = name;
		this.type = type;
		this.menuItems = menuItems;
		this.opened = opened;
		this.lokacija = lokacija;
		this.images = images;
		this.comments = comments;
		this.manager = manager;
		this.ocena = ocena;
	}
	
	
}
