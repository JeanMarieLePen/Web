package model;
import java.util.ArrayList;

public class Restoran {
	private String name;
	private String type;
	private ArrayList<String> menuItems = new ArrayList<String>();
	private boolean opened;
	private Lokacija lokacija;
	private String image;
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
	public ArrayList<String> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(ArrayList<String> menuItems) {
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Restoran(String name, String type, ArrayList<String> menuItems, boolean opened, Lokacija lokacija,
			String image) {
		super();
		this.name = name;
		this.type = type;
		this.menuItems = menuItems;
		this.opened = opened;
		this.lokacija = lokacija;
		this.image = image;
	}
	
	
	
}
