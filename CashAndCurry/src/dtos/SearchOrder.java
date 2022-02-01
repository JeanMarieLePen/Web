package dtos;

public class SearchOrder {

	private String datumOd;
	private String datumDo;
	private int cenaOd;
	private int cenaDo;
	private String nazivRestorana;
	
	public SearchOrder() {
		super();
	}
	public SearchOrder(String datumOd, String datumDo, int cenaOd, int cenaDo, String nazivRestorana) {
		super();
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.cenaOd = cenaOd;
		this.cenaDo = cenaDo;
		this.nazivRestorana = nazivRestorana;
	}
	public String getDatumOd() {
		return datumOd;
	}
	public void setDatumOd(String datumOd) {
		this.datumOd = datumOd;
	}
	public String getDatumDo() {
		return datumDo;
	}
	public void setDatumDo(String datumDo) {
		this.datumDo = datumDo;
	}
	public int getCenaOd() {
		return cenaOd;
	}
	public void setCenaOd(int cenaOd) {
		this.cenaOd = cenaOd;
	}
	public int getCenaDo() {
		return cenaDo;
	}
	public void setCenaDo(int cenaDo) {
		this.cenaDo = cenaDo;
	}
	public String getNazivRestorana() {
		return nazivRestorana;
	}
	public void setNazivRestorana(String nazivRestorana) {
		this.nazivRestorana = nazivRestorana;
	}
	
}
