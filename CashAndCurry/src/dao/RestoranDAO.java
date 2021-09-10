package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import jersey.repackaged.com.google.common.base.Predicates;
import jersey.repackaged.com.google.common.collect.Collections2;
import model.Artikal;
import model.Customer;
import model.Manager;
import model.Restoran;

public class RestoranDAO {
		private Map<String, Restoran> restorani = new HashMap<>();
		private String contextPath;
		
		public RestoranDAO()
		{
			
		}
		
		public RestoranDAO(String contextPath)
		{
			this.contextPath = contextPath;
			loadRestorani(contextPath);
		}
		
		public void loadRestorani(String contextPath) {
			BufferedReader in = null;
			try {
				
				JsonReader reader = new JsonReader(new FileReader(contextPath + "restorani.json"));
				Gson gson = new Gson();
				Restoran[] tempRestorani = gson.fromJson(reader, Restoran[].class);
				if(tempRestorani != null) {
					for(Restoran c : tempRestorani) {
						restorani.put(c.getName(), c);
					}
				}

			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
		}
		
		
//		public void loadRestorani(String contextPath)
//		{
//			BufferedReader in = null;
//			try
//			{	
//					JsonReader reader = new JsonReader(new FileReader(contextPath + "restorani.json"));
//					Gson gson = new Gson();
//					Restoran[] tempRestorani = gson.fromJson(reader, Restoran[].class);
//					for(Restoran c : tempRestorani) {
//						restorani.put(c.getName(), c);
//				}
//				
//			}catch(Exception ex) {
//				ex.printStackTrace();
//			}
//		}
		public Collection<Restoran> findAllRestorani()
		{
			return restorani.values();
		}
		public Restoran findRestoran(String name)
		{
			Restoran r = restorani.containsKey(name) ? restorani.get(name) : null;
			return r;
		}
		
		
		public Restoran getRestoranByManager(String username) {
			Collection<Restoran> temp = restorani.values();
			Restoran r = new Restoran();
			//u for petlji dobijarmo restoran r u kojem radi prosledjeni menadzer
			for(Restoran tempRestoran : restorani.values()) {
				if(tempRestoran.getManager().getUsername().equals(username)) {
					r = tempRestoran;
					break;
				}
			}
			return r;
		}
		//dodavanje novog Customera i njegovo cuvanje u bazu
		public Restoran addNewRestoran(Restoran restoran) {
			System.out.println("USLO U ADDNEWRESTORAN");
			if(!restorani.containsKey(restoran.getName())) {
				restorani.put(restoran.getName(), restoran);
				Gson gson = new Gson();
				String temp = gson.toJson(restorani);
				
				Collection<Restoran> tmp = this.restorani.values();
				String fileInput = gson.toJson(tmp);
				
				try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "restorani.json", false))){
					System.out.println("Upis novog restorana u bazu.");
					bw.append(fileInput);
					bw.append("\n");
					bw.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
				return restoran;
			}	
			return null;
		}
		
		//izmena postojeceg korisnika i njegovo cuvanje u bazu
		public Restoran updateRestoran(Restoran restoran) {
			if(restorani.containsKey(restoran.getName())) {
				restorani.replace(restoran.getName(), restoran);
				Gson gson = new Gson();
				String temp = gson.toJson(restorani);
				try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "restorani.json", false))){
					bw.append(temp);
					bw.append("\n");
					bw.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
				return restoran;
			}
			return null;
		}
		
		//brisanje korisnika iz baze
		public Restoran removeRestoran(Restoran restoran) {
			if(restorani.containsKey(restoran.getName())) {
				restorani.remove(restoran.getName());
				Gson gson = new Gson();
				String temp = gson.toJson(restorani);
				try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "restorani.json", false))){
					bw.append(temp);
					bw.append("\n");
					bw.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
				return restoran;
			}
			return null;
		}
		
		public Collection<Restoran> findFilteredResults(Restoran restoran){
			Collection<Restoran> tmp = new ArrayList<Restoran>();
			
//			Predicate<Restoran> nameFilter = e -> e.getName().equals(restoran.getName()) && e.getType().equals(restoran.getType());
			Predicate<Restoran> nameFilter = e -> e.getName().equals(restoran.getName());
			Predicate<Restoran> lokacijaFilter = e -> e.getLokacija().getAddress().equals(restoran.getLokacija().getAddress()); 
			Predicate<Restoran> tipFilter = e -> e.getType().equals(restoran.getType());
			Predicate<Restoran> ocenaFilter = e -> e.getOcena() >= restoran.getOcena();
//			if(restoran.getName()!= "" && restoran.getLokacija() != null && restoran.getOcena() > 0 && restoran.getType() != "") {
//				tmp = (Collection<Restoran>) restorani.values().stream().filter(nameFilter.and(lokacijaFilter.and(tipFilter).and(ocenaFilter)));
//			}
//			else if(restoran.getName()!= "" && restoran.getLokacija() != null && restoran.getOcena() > 0) {
//				tmp = (Collection<Restoran>) restorani.values().stream().filter(nameFilter.and(lokacijaFilter.and(ocenaFilter)));
//			}
//			else if(restoran.getName()!= "" && restoran.getLokacija() != null) {
//				tmp = (Collection<Restoran>) restorani.values().stream().filter(nameFilter.and(lokacijaFilter));
//			}else if(restoran.getName()!= "") {
//				tmp = (Collection<Restoran>) restorani.values().stream().filter(nameFilter);
//			}
					
//			tmp = this.restorani.values().stream().filter(nameFilter.and(lokacijaFilter.and(tipFilter).and(ocenaFilter))).collect(Collectors.toList());
//			tmp = restorani.values().stream().filter(nameFilter).collect(Collectors.toList());
//			System.out.println("Pronadjeno je: " + tmp.size() + " restorana koji zadovoljavaju kriterijume.");
//			tmp = tmp.stream().filter(nameFilter.and(tipFilter)).collect(Collectors.toList());
			tmp = restorani.values().stream().filter(nameFilter).collect(Collectors.toList());
			System.out.println("Pronadjeno je: " + tmp.size() + " restorana koji zadovoljavaju kriterijume.");
			return tmp;
			
		}
		
		public Collection<Restoran> findAllRestaurantsWithNoManagers(){
			Collection<Restoran> tmp = new ArrayList<Restoran>();
			for(Restoran r : restorani.values()) {
				if(r.getManager().getUsername().equals("")) {
					tmp.add(r);
				}
			}
			System.out.println("Broj svih menadzera: " + tmp.size());
			return tmp;
		}
		
		public Collection<Restoran> findAllRestaurantsWithManagers(){
			Collection<Restoran> tmp = new ArrayList<Restoran>();
			for(Restoran r : restorani.values()) {
				if(!r.getManager().getUsername().equals("")) {
					tmp.add(r);
				}
			}
			System.out.println("Broj restorana koji imaju menadzere: " + tmp.size());
			return tmp;
		}
		
		public Collection<Artikal> getProducts(String username){
			Collection<Restoran> temp = restorani.values();
			Restoran r = new Restoran();
			//u for petlji dobijarmo restoran r u kojem radi prosledjeni menadzer
			for(Restoran tempRestoran : restorani.values()) {
				if(tempRestoran.getManager().getUsername().equals(username)) {
					r = tempRestoran;
					break;
				}
			}
			
			return r.getMenuItems();
		}
		
		public Artikal addArtikal(String username, Artikal a) {
			Collection<Restoran> temp = restorani.values();
			Restoran r = new Restoran();
			//u for petlji dobijarmo restoran r u kojem radi prosledjeni menadzer
			for(Restoran tempRestoran : restorani.values()) {
				if(tempRestoran.getManager().getUsername().equals(username)) {
					r = tempRestoran;
					break;
				}
			}
			
			for(Artikal tempArt : r.getMenuItems()) {
				if(a.getNaziv().equals(tempArt.getNaziv())) {
					return null;
				}
			}
			r.getMenuItems().add(a);
			
			
			Gson gson = new Gson();
			Collection<Restoran> tmp = this.restorani.values();
			String fileInput = gson.toJson(tmp);
			
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "restorani.json", false))){
				System.out.println("Upis novog restorana u bazu.");
				bw.append(fileInput);
				bw.append("\n");
				bw.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
	
			
			return a;
		}
		
}
