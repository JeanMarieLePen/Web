package model;

public class Lokacija {
	private String longitude;
	private String latitude;
	private String address;
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Lokacija(String longitude, String latitude, String address) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.address = address;
	}
	
	
}
