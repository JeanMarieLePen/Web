package dao;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import model.Customer;
import model.Komentar;
import model.Restoran;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class KomentarDAO {

	private Map<String, Komentar> comments = new HashMap<>();
	private Map<String, Restoran> restorani = new HashMap<>();
	
	public KomentarDAO() {
		
	}
	public KomentarDAO(String contextPath) {
		loadComments(contextPath);
	}
	public void loadComments(String contextPath) {
		ArrayList<Restoran> restaurants = new ArrayList<Restoran>();
		try {
			JsonReader reader = new JsonReader(new FileReader("restorani.json"));
			Gson gson = new Gson();
			Restoran[] tempRestorani = gson.fromJson(reader, Restoran[].class);
			for(Restoran r : tempRestorani) {
				restaurants.add(r);
				restorani.put(r.getName(), r);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
			
	}
	//metoda koja vraca listu svih komentara o jednom restoranu
	public Collection<Komentar> findAllKomentare(String nazivRestorana){
		if(restorani.containsKey(nazivRestorana)) {
			return restorani.get(nazivRestorana).getComments();
		}
		return null;
	}
	//metoda koja vraca komentare koje je ostavio jedan korisnik o nekom restoranu
	public Collection<Komentar> findKomentar(String username, String nazivRestorana) {
		Collection<Komentar> komentariKorisnika = new ArrayList<Komentar>();
		if(restorani.containsKey(nazivRestorana)) {
			ArrayList<Komentar> tempLista = restorani.get(nazivRestorana).getComments();
			
			for(Komentar k : tempLista) {
				if(k.getKupacKomentator().equals(username)) {
					komentariKorisnika.add(k);
				}
			}
		}
		return komentariKorisnika;
	}
	
	
	public Komentar save(String nazivRestorana, Komentar comment) {
		restorani.get(nazivRestorana).getComments().add(comment);
		Gson gson = new Gson();
		String temp = gson.toJson(restorani);
		try(BufferedWriter bw = new BufferedWriter(new FileWriter("restorani.json", false))){
			bw.append(temp);
			bw.append("\n");
			bw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return comment;
	}
	//ne nudimo mogucnost naknadne izmene komentara(za sada)
//	/*
//	public Komentar update() {
//		
//	}*/
	
	//brisanje odredjenog komentara(potrebno u model klasi Komentar dodati atribut idKomentara
	//koji bi sluzio za laku identifikaciju komentara koji zelimo da obrisemo
	
	
	
//	public boolean removeKomentar(String nazivRestorana, String idKomentara) {
//		
//		if(restorani.get(nazivRestorana).getComments() != null) {
//			ArrayList<Komentar> tempComments = restorani.get(nazivRestorana).getComments();
//			for(Komentar k : tempComments) {
//				if(k.getId().equals(idKomentara)) {
//					tempComments.remove(k);
//					return true;
//				}
//			}
//		}
//		return false;
//	}
}
