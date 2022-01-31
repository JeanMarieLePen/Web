package model;

public class Komentar {
	
	private int id;
	private boolean odobren = false;
	private String kupacKomentator;
	private String komentarisaniRestoran;
	private String tekstKomentara;
	private int ocena;
	private boolean obrisanOdModeratora = false;
	private boolean obrisanOdKupca = false;
	public Komentar(boolean odobren, String kupacKomentator, String komentarisaniRestoran, String tekstKomentara,
			int ocena, boolean obrisanOdModeratora, boolean obrisanOdKupca) {
		super();
		this.odobren = odobren;
		this.kupacKomentator = kupacKomentator;
		this.komentarisaniRestoran = komentarisaniRestoran;
		this.tekstKomentara = tekstKomentara;
		this.ocena = ocena;
		this.obrisanOdModeratora = obrisanOdModeratora;
		this.obrisanOdKupca = obrisanOdKupca;
	}

	public Komentar() {
		super();
	}
	
	public boolean isObrisanOdKupca() {
		return obrisanOdKupca;
	}

	public void setObrisanOdKupca(boolean obrisanOdKupca) {
		this.obrisanOdKupca = obrisanOdKupca;
	}

	public int getId() {
		return id;
	}
	public boolean isObrisanOdModeratora() {
		return obrisanOdModeratora;
	}

	public void setObrisanOdModeratora(boolean obrisanOdModeratora) {
		this.obrisanOdModeratora = obrisanOdModeratora;
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
