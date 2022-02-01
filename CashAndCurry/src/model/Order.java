package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Order {

	private int idPorudzbine;
	private ArrayList<ArtikalOrder> listaNarucenihArtikala = new ArrayList<ArtikalOrder>();
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
	private String idDeliveryMana;

	public Order() {
		
	}
	public Order(ArrayList<ArtikalOrder> listaNarucenihArtikala, String izKogRestorana, String datumIVremePorudzbine,
			int cena, String idKupca, String statusPorudzbine, String idDeliveryMana) {
		super();
		this.listaNarucenihArtikala = listaNarucenihArtikala;
		this.izKogRestorana = izKogRestorana;
		this.datumIVremePorudzbine = datumIVremePorudzbine;
		this.cena = cena;
		this.idKupca = idKupca;
		this.statusPorudzbine = statusPorudzbine;
		this.idDeliveryMana = idDeliveryMana;
	}

	public Date datumToDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH);

		Date date = new Date();
		try {
			date = formatter.parse(this.datumIVremePorudzbine);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public String getIdDeliveryMana() {
		return idDeliveryMana;
	}
	public void setIdDeliveryMana(String idDeliveryMana) {
		this.idDeliveryMana = idDeliveryMana;
	}
	public int getIdPorudzbine() {
		return idPorudzbine;
	}
	public void setIdPorudzbine(int idPorudzbine) {
		this.idPorudzbine = idPorudzbine;
	}
	public ArrayList<ArtikalOrder> getListaNarucenihArtikala() {
		return listaNarucenihArtikala;
	}

	public void setListaNarucenihArtikala(ArrayList<ArtikalOrder> listaNarucenihArtikala) {
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
