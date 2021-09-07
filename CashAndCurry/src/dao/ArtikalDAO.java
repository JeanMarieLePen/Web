package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import model.Artikal;
import model.Restoran;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class ArtikalDAO {
	private Map<String, Restoran> restorani = new HashMap<>();
	private String contextPath;
	public ArtikalDAO() {
		
	}
	public ArtikalDAO(String contextPath) {
		this.contextPath = contextPath;
		loadArtikle(contextPath);
	}
	public void loadArtikle(String contextPath) {
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
	//metoda koja pronalazi sve artikle koje nudi jedan restoran
	public Collection<Artikal> findAllArtikli(String nazivRestorana){
		if(restorani.containsKey(nazivRestorana)) {
			return (Collection<Artikal>)restorani.get(nazivRestorana).getMenuItems();
		}
		return null;
	}
	//metoda koja pretrazuje sve restorane u cijoj ponudi se nalazi trazeni artikal
	//i kao povratnu vrednost vraca detalje o tom artiklu, za svaki restoran posebno
	public Collection<Artikal> findArtikal(String nazivArtikla) {
		Collection<Artikal> tempArtikli = new ArrayList<Artikal>();
		for(Restoran r : restorani.values()) {
			for(Artikal a : r.getMenuItems()) {
				if(a.getNaziv().equals(nazivArtikla)) {
					tempArtikli.add(a);
				}
			}
		}
		return tempArtikli;
	}
	//metoda koja dodaje novi artikal u ponudu restorana i smesta ga u bazu
	public Artikal addArtikal(String nazivRestorana, Artikal artikal) {
		if(restorani.containsKey(nazivRestorana)) {
			Map<String, Artikal> mapaArtikala = new HashMap<String, Artikal>();
			for(Artikal art : restorani.get(nazivRestorana).getMenuItems()) {
				mapaArtikala.put(art.getNaziv(), art);
			}
			if(!mapaArtikala.containsKey(artikal.getNaziv())) {
				restorani.get(nazivRestorana).getMenuItems().add(artikal);
			}
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
			return artikal;
		}
		return null;
	}
	//metoda koja apdejtuje postojeci artikal i upisuje ga u bazu
	public Artikal updateArtikal(String nazivRestorana, Artikal artikal) {
		if(restorani.containsKey(nazivRestorana)) {
			int i = 0;
			for(Artikal a : restorani.get(nazivRestorana).getMenuItems()) {
				if(a.getNaziv().equals(artikal.getNaziv())) {
					restorani.get(nazivRestorana).getMenuItems().set(i, artikal);
					i++;
				}
			}
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
			return artikal;
		}
		return null;
	}
	//metoda koja uklanja zeljeni artikal iz ponude restorana i izmene unosi u bazu
	public Artikal removeArtikal(String nazivRestorana, String nazivArtikla) {
		if(restorani.containsKey(nazivRestorana)) {
			Artikal art = new Artikal();
			for(Artikal a : restorani.get(nazivRestorana).getMenuItems()) {
				if(a.getNaziv().equals(nazivArtikla)) {
					art = a;
					restorani.get(nazivRestorana).getMenuItems().remove(a);
				}
			}
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
			return art;
		}
		return null;
	}
}


