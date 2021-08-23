package model;

import java.util.ArrayList;

public class Order {

	private String idPorudzbine;
	private ArrayList<String> listaNarucenihArtikala = new ArrayList<String>();
	private String izKogRestorana;
	private String datumIVremePorudzbine;
	private int cena;
	private Customer kupac;
	//obrada, u pripremi, ceka dostavljaca, u transportu, dostavljena, otkazana
	private String statusPorudzbine;
	public Order() {
		
	}
}
