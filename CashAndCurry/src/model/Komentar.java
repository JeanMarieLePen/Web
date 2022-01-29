package model;

public class Komentar {
	
	private int id;
	private boolean odobren = false;
	private String kupacKomentator;
	private String komentarisaniRestoran;
	private String tekstKomentara;
	private int ocena;
	public Komentar() {
		super();
	}
	public Komentar(String kupacKomentator, String komentarisaniRestoran, String tekstKomentara, int ocena, boolean odobren) {
		super();
		this.kupacKomentator = kupacKomentator;
		this.komentarisaniRestoran = komentarisaniRestoran;
		this.tekstKomentara = tekstKomentara;
		this.ocena = ocena;
		this.odobren = odobren;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKupacKomentator() {
		return kupacKomentator;
	}
	public void setKupacKomentator(String kupacKomentator) {
		this.kupacKomentator = kupacKomentator;
	}
	public String getKomentarisaniRestoran() {
		return komentarisaniRestoran;
	}
	public void setKomentarisaniRestoran(String komentarisaniRestoran) {
		this.komentarisaniRestoran = komentarisaniRestoran;
	}
	public String getTekstKomentara() {
		return tekstKomentara;
	}
	public void setTekstKomentara(String tekstKomentara) {
		this.tekstKomentara = tekstKomentara;
	}
	public int getOcena() {
		return ocena;
	}
	public void setOcena(int ocena) {
		this.ocena = ocena;
	}
	public boolean isOdobren() {
		return odobren;
	}
	public void setOdobren(boolean odobren) {
		this.odobren = odobren;
	}
}
