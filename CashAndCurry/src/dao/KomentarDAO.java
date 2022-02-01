package dao;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import model.Customer;
import model.Komentar;
import model.Manager;
import model.Restoran;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class KomentarDAO {

	private Map<String, Komentar> comments = new HashMap<>();
	private Map<String, Restoran> restorani = new HashMap<>();
	private String contextPath;
	public KomentarDAO() {
		
	}
	public KomentarDAO(String contextPath) {
		this.contextPath = contextPath;
		loadComments(contextPath);
	}
	public void loadComments(String contextPath) {
		ArrayList<Restoran> restaurants = new ArrayList<Restoran>();
		try {
			JsonReader reader = new JsonReader(new FileReader(contextPath + "restorani.json"));
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
	
	//metoda koja vraca listu svih NEODOBRENIH komentara o jednom restoranu[STRIKTNO ZA MENADZERA]
	public Collection<Komentar> findAllUnmoderatedComments(String nazivManagera){
		loadComments(contextPath);
		Restoran r = this.getRestoranByNazivManagera(nazivManagera);
		ArrayList<Komentar> listaKomentara = new ArrayList<Komentar>();
		for(Komentar k : r.getComments()) {
			if(k.isOdobren() == false) {
				listaKomentara.add(k);
			}
		}
		if(listaKomentara.size() > 0) {
			return listaKomentara;
		}
		return null;
	}
	
	//metoda koja brise komentar[sa aspekta MANAGER-a]
	public Komentar removeComment(Komentar k) {
		if(this.restorani.containsKey(k.getKomentarisaniRestoran())) {
			ArrayList<Komentar> listaKomentara = this.restorani.get(k.getKomentarisaniRestoran()).getComments();
			for(Komentar temp: listaKomentara) {
				if(temp.getId() == k.getId()) {
					temp.setObrisanOdModeratora(true);
					break;
				}
			}
			return k;
		}
		return null;
	}
	
	//metoda koja brise komentar[sa aspekta USER-a]
		public Komentar removeCommentUser(Komentar k) {
			if(this.restorani.containsKey(k.getKomentarisaniRestoran())) {
				ArrayList<Komentar> listaKomentara = this.restorani.get(k.getKomentarisaniRestoran()).getComments();
				for(Komentar temp: listaKomentara) {
					if(temp.getId() == k.getId()) {
						temp.setObrisanOdKupca(true);
						break;
					}
				}
				return k;
			}
			return null;
		}
	
	public Restoran getRestoranByNazivManagera(String nazivManagera) {
		Restoran temp = null;
		for(Restoran r : restorani.values()) {
			if(r.getManager().equals(nazivManagera)) {
				temp = r;
				return temp;
			}
		}
		return temp;
	}
	
	
	//metoda koja vraca listu svih ODOBRENIH komentara o jednom restoranu na osnovu imena restorana[KUPAC]
		public Collection<Komentar> findAllKomentareForCustomer(String name){
			if(this.restorani.containsKey(name)) {
				return restorani.get(name).getComments();
			}
			return null;
		}
	
	
	
	//metoda koja vraca listu svih ODOBRENIH komentara o jednom restoranu
	public Collection<Komentar> findAllKomentare(String nazivManagera){
		Restoran r = this.getRestoranByNazivManagera(nazivManagera);
		ArrayList<Komentar> listaKomentara = new ArrayList<Komentar>();
		for(Komentar k : r.getComments()) {
			if(k.isOdobren() == true && k.isObrisanOdKupca() == false && k.isObrisanOdModeratora() == false) {
				listaKomentara.add(k);
			}
		}
		if(listaKomentara.size() > 0) {
			return listaKomentara;
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
	
	public int calculateOcena(ArrayList<Komentar> listaKomentara) {
		int temp = 0;
		for(Komentar k : listaKomentara) {
			temp += k.getOcena();
		}
		int ocena = temp / listaKomentara.size();
		return ocena;
	}
	
	public int generateId(String naziv) {
		int generatedId = -1;
		if(restorani.containsKey(naziv)) {
			ArrayList<Komentar> listaKomentara = this.restorani.get(naziv).getComments();
			List<Integer> listIds = new ArrayList<Integer>();
			for(Komentar k : listaKomentara) {
				listIds.add(k.getId());
			}
			
			for(int i = 0; i < listIds.size() + 1; i++) {
				if(!listIds.contains(i)) {
					generatedId = i;
					break;
				}
			}
		}
		
		return generatedId;
	}
	
	public Collection<Komentar> updateModeratedComments(String name, ArrayList<Komentar> listaKomentara){
		Restoran r = this.getRestoranByNazivManagera(name);
		if(r != null) {
			Map<Integer, Komentar> komentari = new HashMap<>();
			for(Komentar k : r.getComments()) {
				komentari.put(k.getId(), k);
			}
			
			for(Komentar cmnt : listaKomentara) {
				komentari.replace(cmnt.getId(), cmnt);
			}
			
			ArrayList<Komentar> tempList = (ArrayList<Komentar>) komentari.values().stream().collect(Collectors.toList());
			r.setComments(tempList);
			
			Gson gson = new Gson();
			String temp = gson.toJson(this.restorani.values());
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "restorani.json", false))){
				bw.append(temp);
				bw.append("\n");
				bw.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Komentar save(Komentar comment) {
		String nazivRestorana = comment.getKomentarisaniRestoran();
		comment.setId(this.generateId(nazivRestorana));
		restorani.get(nazivRestorana).getComments().add(comment);
		restorani.get(nazivRestorana).setOcena(this.calculateOcena(restorani.get(nazivRestorana).getComments()));
		System.out.println("Trenutna ocena restorana: " + nazivRestorana + " je " + restorani.get(nazivRestorana).getOcena());
		Gson gson = new Gson();
		//ovde sam imao gresku jer sam u fajl upisivao mapu a ne niz restorana
		String temp = gson.toJson(this.restorani.values());
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "restorani.json", false))){
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
	

}
