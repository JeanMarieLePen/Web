package model;

public class Artikal {
	private String naziv;
	private int cena;
	//tip = jelo ili pice
	private enum Tip{
		jelo, pice
	}
	private String tip;
	private String restoranKomPripada;
	private int kolicina;
	private String opis;
	private String slika;
	
	public Artikal(String naziv, int cena, String tip, String restoranKomPripada, int kolicina, String opis,
			String slika) {
		super();
		this.naziv = naziv;
		this.cena = cena;
		this.tip = tip;
		this.restoranKomPripada = restoranKomPripada;
		this.kolicina = kolicina;
		this.opis = opis;
		this.slika = slika;
	}
	public Artikal() {
		// TODO Auto-generated constructor stub
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
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String getRestoranKomPripada() {
		return restoranKomPripada;
	}
	public void setRestoranKomPripada(String restoranKomPripada) {
		this.restoranKomPripada = restoranKomPripada;
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
