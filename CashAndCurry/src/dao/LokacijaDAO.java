package dao;

import model.Restoran;

import java.util.ArrayList;
import java.util.Collection;

import model.Lokacija;

import java.util.Map;

import java.util.HashMap;
import java.util.Iterator;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class LokacijaDAO {

	private Map<String, Lokacija> locations = new HashMap<>();
	private Map<String, Restoran> restorani = new HashMap<>();
	private String contextPath;
	public LokacijaDAO() {
		
	}
	public LokacijaDAO(String contextPath, String name) {
		this.contextPath = contextPath;
		loadLokacije(contextPath, name);
	}
	public void loadLokacije(String contextPath, String name) {
		try {
			JsonReader reader = new JsonReader(new FileReader(contextPath + "restaurants.json"));
			Gson gson = new Gson();
			Restoran[] tempRestorani = gson.fromJson(reader, Restoran[].class);
			for(Restoran r: tempRestorani) {
				restorani.put(r.getName(), r);
			}
				
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	//metoda koja pronalazi lokacije svih restorana(moze biti korisna ako cemo nuditi onu funkcionalnost
	//nadji sve restorane u krugu od...
	public Collection<Lokacija> findAllLocations(){
		Collection<Lokacija> tempLokacije = new ArrayList<Lokacija>();
		for(Restoran r : restorani.values()) {
			tempLokacije.add(r.getLokacija());
		}
		return tempLokacije;
	}
	//metoda koja vraca lokaciju konkretnog restorana
	public Lokacija findLokacija(String nazivRestorana) {
		if(restorani.containsKey(nazivRestorana)) {
			return restorani.get(nazivRestorana).getLokacija();
		}
		return null;
	}
	
	//posto lokacija ne moze samostalno da se dodaje u bazu vec se dodaje u okviru entiteta restoran
	//addLokacija nece postojati, vec cemo imati samo update
	public Lokacija updateLocation(Lokacija l, String imeRestorana) {
		//Mapa restorani sadrzi sve restorane u bazi. postavljamo lokaciju na novu vrednost
		restorani.get(imeRestorana).setLokacija(l);
		
		Gson gson = new Gson();
		String temp = gson.toJson(restorani);
		//false kao parametar jer zelimo ponovni upis celog fajla
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "restaurants.json", false))){
			bw.append(temp);
			bw.append("\n");
			bw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return l;
	}
	//ne bi trebaloda bude moguce obrisati lokaciju restorana tako da ovo ne pravimo
//	public Lokacija removeLokacija(String nazivRestorana) {
//		
//	}
	
}
