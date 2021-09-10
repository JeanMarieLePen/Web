package model;

public class Artikal {
	
	private String naziv;
	private int cena;
	//tip = jelo ili pice
	private String typeOfArtikal;
	private int kolicina;
	private String opis;
	private String slika;
	
	public Artikal() {
		
	}

	public Artikal(String naziv, int cena, String typeOfArtikal, int kolicina, String opis, String slika) {
		super();
		this.naziv = naziv;
		this.cena = cena;
		this.typeOfArtikal = typeOfArtikal;
		this.kolicina = kolicina;
		this.opis = opis;
		this.slika = slika;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public int getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	public String getTypeOfArtikal() {
		return typeOfArtikal;
	}

	public void setTypeOfArtikal(String typeOfArtikal) {
		this.typeOfArtikal = typeOfArtikal;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getSlika() {
		return slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}
	
	
	
}
