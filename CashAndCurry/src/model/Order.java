package model;

import java.util.ArrayList;

public class Order {

	private String idPorudzbine;
	private ArrayList<String> listaNarucenihArtikala = new ArrayList<String>();
	private String izKogRestorana;
	private String datumIVremePorudzbine;
	private int cena;
	private String idKupca;
//	private Customer kupac;
	//obrada, u pripremi, ceka dostavljaca, u transportu, dostavljena, otkazana
//	private enum status{
//		obrada, uPripremi, cekaDostavljaca, uTransportu, dostvaljena, otkazana
//	}
	private String statusPorudzbine;

	public Order() {
		
	}

	public Order(ArrayList<String> listaNarucenihArtikala, String izKogRestorana, String datumIVremePorudzbine,
			int cena, String idKupca, String statusPorudzbine) {
		super();
		this.listaNarucenihArtikala = listaNarucenihArtikala;
		this.izKogRestorana = izKogRestorana;
		this.datumIVremePorudzbine = datumIVremePorudzbine;
		this.cena = cena;
		this.idKupca = idKupca;
		this.statusPorudzbine = statusPorudzbine;
	}

	public String getIdPorudzbine() {
		return idPorudzbine;
	}

	public void setIdPorudzbine(String idPorudzbine) {
		this.idPorudzbine = idPorudzbine;
	}

	public ArrayList<String> getListaNarucenihArtikala() {
		return listaNarucenihArtikala;
	}

	public void setListaNarucenihArtikala(ArrayList<String> listaNarucenihArtikala) {
		this.listaNarucenihArtikala = listaNarucenihArtikala;
	}

	public String getIzKogRestorana() {
		return izKogRestorana;
	}

	public void setIzKogRestorana(String izKogRestorana) {
		this.izKogRestorana = izKogRestorana;
	}

	public String getDatumIVremePorudzbine() {
		return datumIVremePorudzbine;
	}

	public void setDatumIVremePorudzbine(String datumIVremePorudzbine) {
		this.datumIVremePorudzbine = datumIVremePorudzbine;
	}

	public int getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	public String getIdKupca() {
		return idKupca;
	}

	public void setIdKupca(String idKupca) {
		this.idKupca = idKupca;
	}

	public String getStatusPorudzbine() {
		return statusPorudzbine;
	}

	public void setStatusPorudzbine(String statusPorudzbine) {
		this.statusPorudzbine = statusPorudzbine;
	}
}
