package model;

public class ArtikalOrder  {

	private int brojKomada;
	private String nazivArtikla;

	public int getBrojKomada() {
		return brojKomada;
	}

	public ArtikalOrder() {
		super();
	}
	
	public ArtikalOrder(int brojKomada, String nazivArtikla) {
		super();
		this.brojKomada = brojKomada;
		this.nazivArtikla = nazivArtikla;
	}

	public void setBrojKomada(int brojKomada) {
		this.brojKomada = brojKomada;
	}

	public String getNazivArtikla() {
		return nazivArtikla;
	}

	public void setNazivArtikla(String nazivArtikla) {
		this.nazivArtikla = nazivArtikla;
	}
	
	
}
