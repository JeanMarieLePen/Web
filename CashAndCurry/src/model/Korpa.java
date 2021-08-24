package model;

import java.util.ArrayList;

public class Korpa {

	private ArrayList<Artikal> sadrzajKorpe = new ArrayList<Artikal>();
	private String vlasnikKorpe;
	private int cena;
	public ArrayList<Artikal> getSadrzajKorpe() {
		return sadrzajKorpe;
	}
	public void setSadrzajKorpe(ArrayList<Artikal> sadrzajKorpe) {
		this.sadrzajKorpe = sadrzajKorpe;
	}
	public String getVlasnikKorpe() {
		return vlasnikKorpe;
	}
	public void setVlasnikKorpe(String vlasnikKorpe) {
		this.vlasnikKorpe = vlasnikKorpe;
	}
	public int getCena() {
		return cena;
	}
	public void setCena(int cena) {
		this.cena = cena;
	}
	public Korpa(ArrayList<Artikal> sadrzajKorpe, String vlasnikKorpe, int cena) {
		super();
		this.sadrzajKorpe = sadrzajKorpe;
		this.vlasnikKorpe = vlasnikKorpe;
		this.cena = cena;
	}
	
	
}
