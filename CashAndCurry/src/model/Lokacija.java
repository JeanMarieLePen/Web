package model;

public class Lokacija {
	
	private int id;
	private String ulica;
	private String broj;
	private String mesto;
	private String drzava;
	private float xCoord;
	private float yCoord;
	
	
	public Lokacija() {
		super();
	}
	public Lokacija(String ulica, String broj, String mesto, String drzava, float xCoord, float yCoord) {
		super();
		this.ulica = ulica;
		this.broj = broj;
		this.mesto = mesto;
		this.drzava = drzava;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUlica() {
		return ulica;
	}
	public void setUlica(String ulica) {
		this.ulica = ulica;
	}
	public String getBroj() {
		return broj;
	}
	public void setBroj(String broj) {
		this.broj = broj;
	}
	public String getMesto() {
		return mesto;
	}
	public void setMesto(String mesto) {
		this.mesto = mesto;
	}
	public String getDrzava() {
		return drzava;
	}
	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}
	public float getxCoord() {
		return xCoord;
	}
	public void setxCoord(float xCoord) {
		this.xCoord = xCoord;
	}
	public float getyCoord() {
		return yCoord;
	}
	public void setyCoord(float yCoord) {
		this.yCoord = yCoord;
	}
	
	
	
}
