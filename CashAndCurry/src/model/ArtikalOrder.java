package model;

public class ArtikalOrder extends Artikal {

	private int brojKomada;

	public int getBrojKomada() {
		return brojKomada;
	}

	public ArtikalOrder() {
		super();
	}

	
	public ArtikalOrder(String naziv, int cena, String typeOfArtikal, int kolicina, String opis, String slika,
			int brojKomada) {
		super(naziv, cena, typeOfArtikal, kolicina, opis, slika);
		this.brojKomada = brojKomada;
	}

	public void setBrojKomada(int brojKomada) {
		this.brojKomada = brojKomada;
	}
	
	
}
