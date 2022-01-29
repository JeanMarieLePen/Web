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
import java.util.List;
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
import model.Komentar;
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
		
		public int generateId(String naziv) {
			int generatedId = -1;
			if(restorani.containsKey(naziv)) {
				ArrayList<Restoran> listaRestorana = (ArrayList<Restoran>) this.restorani.values().stream().collect(Collectors.toList());
				List<Integer> listIds = new ArrayList<Integer>();
				
				for(Restoran r : listaRestorana) {
					listIds.add(r.getId());
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
		
		public Restoran getRestoranByManager(String username) {
			Collection<Restoran> temp = restorani.values();
			Restoran r = new Restoran();
			//u for petlji dobijarmo restoran r u kojem radi prosledjeni menadzer
			for(Restoran tempRestoran : restorani.values()) {
				if(tempRestoran.getManager().equals(username)) {
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
				restorani.get(restoran.getName()).setId(this.generateId(restoran.getName()));
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
			Collection<Restoran> test = new ArrayList<Restoran>();
			Collection<Restoran> tmp2 = new ArrayList<Restoran>();
			Collection<Restoran> tmp3 = new ArrayList<Restoran>();
			Collection<Restoran> tmp4 = new ArrayList<Restoran>();
			
//			Predicate<Restoran> nameFilter = e -> e.getName().equals(restoran.getName()) && e.getType().equals(restoran.getType());
//			Predicate<Restoran> nameFilter = e -> e.getName().equals(restoran.getName());
			
			//treba da postoji pretraga po drzavi i gradu -> 2 filtera za lokaciju
//			Predicate<Restoran> lokacijaFilter = e -> e.getLokacija().getDrzava().equals(restoran.getLokacija().getDrzava());
//			Predicate<Restoran> lokacijaFilter2 = e -> e.getLokacija().getMesto().equals(restoran.getLokacija().getMesto());
//			Predicate<Restoran> tipFilter = e -> e.getType().equals(restoran.getType());
//			Predicate<Restoran> ocenaFilter = e -> e.getOcena() >= restoran.getOcena();
//
//			Predicate<Restoran> tmpnameFilter = e -> e.getName().toLowerCase().equals(restoran.getName().toLowerCase()) && e.getLokacija().getDrzava().toLowerCase().equals(restoran.getLokacija().getDrzava().toLowerCase());
			
//			tmp = restorani.values().stream().filter(nameFilter).collect(Collectors.toList());
			//test = restorani.values().stream().filter(tmpnameFilter).collect(Collectors.toList());
//			tmp2 = restorani.values().stream().filter(lokacijaFilter).collect(Collectors.toList());
			
			
			
			Predicate<Restoran> filter1; /*= e -> restoran.getName() == "" ? true : e.getName().equals(restoran.getName());*/
			Predicate<Restoran> filter2; /*= e -> restoran.getLokacija().getDrzava() == "" ? true : e.getLokacija().getDrzava().equals(restoran.getLokacija().getDrzava());*/
			Predicate<Restoran> filter3;/*= e -> restoran.getType() == "" ? true : e.getType().equals(restoran.getType());*/
			Predicate<Restoran> filter4;/*= e -> restoran.getOcena() == 0 ? true : e.getOcena() == restoran.getOcena();*/
			
			if(restoran.getName() == null) {
				filter1 = e -> true;
			}else {
				filter1 = e -> e.getName().toLowerCase().equals(restoran.getName().toLowerCase());
			}
			if(restoran.getLokacija() == null) {
				filter2 = e -> true;
			}else {
				filter2 = e -> e.getLokacija().getDrzava().toLowerCase().equals(restoran.getLokacija().getDrzava().toLowerCase());
			}
			if(restoran.getType() == null) {
				filter3 = e -> true;
			}else {
				filter3 = e -> e.getType().toLowerCase().equals(restoran.getType().toLowerCase());
			}
			if(restoran.getOcena() == 0) {
				filter4 = e -> true;
			}else {
				filter4 = e -> e.getOcena() == restoran.getOcena();
			}
			
			
			test = restorani.values().stream().filter(filter1).filter(filter2).filter(filter3).filter(filter4).
					collect(Collectors.toList());
			

			
			
			Collection<Restoran> searchResult = new ArrayList<Restoran>();
			
			for(Restoran r : test) {
				if(test.contains(r)) {
					searchResult.add(r);
				}
			}
			
//			Collection<Restoran> searchResult = new ArrayList<Restoran>();
//			for(Restoran r : tmp) {
//				if(tmp2.contains(r)) {
//					searchResult.add(r);
//				}
//			}
//			System.out.println("Pronadjeno je: " + tmp.size() + " restorana koji zadovoljavaju kriterijume.");
//			return tmp;
			System.out.println("Pronadjeno je: " + searchResult.size() + " restorana koji zadovoljavaju kriterijume.");
			return searchResult;
			
		}
		
		public Collection<Restoran> findAllRestaurantsWithNoManagers(){
			Collection<Restoran> tmp = new ArrayList<Restoran>();
			for(Restoran r : restorani.values()) {
				if(r.getManager().equals("")) {
					tmp.add(r);
				}
			}
			System.out.println("Broj svih menadzera: " + tmp.size());
			return tmp;
		}
		
		public Collection<Restoran> findAllRestaurantsWithManagers(){
			Collection<Restoran> tmp = new ArrayList<Restoran>();
			for(Restoran r : restorani.values()) {
				if(!r.getManager().equals("")) {
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
				if(tempRestoran.getManager().equals(username)) {
					r = tempRestoran;
					break;
				}
			}
			
			return r.getMenuItems();
		}
		
//		public Artikal addArtikal(String username, Artikal a) {
//			Collection<Restoran> temp = restorani.values();
//			Restoran r = new Restoran();
//			//u for petlji dobijarmo restoran r u kojem radi prosledjeni menadzer
//			for(Restoran tempRestoran : restorani.values()) {
//				if(tempRestoran.getManager().equals(username)) {
//					r = tempRestoran;
//					break;
//				}
//			}
//			
//			for(Artikal tempArt : r.getMenuItems()) {
//				if(a.getNaziv().equals(tempArt.getNaziv())) {
//					return null;
//				}
//			}
//			r.getMenuItems().add(a);
//			
//			
//			Gson gson = new Gson();
//			Collection<Restoran> tmp = this.restorani.values();
//			String fileInput = gson.toJson(tmp);
//			
//			try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "restorani.json", false))){
//				System.out.println("Upis novog restorana u bazu.");
//				bw.append(fileInput);
//				bw.append("\n");
//				bw.close();
//			}catch(IOException e) {
//				e.printStackTrace();
//			}
//	
//			
//			return a;
//		}
		
		public Artikal addArtikal(String restaurantName, Artikal a) {
			Collection<Restoran> temp = restorani.values();
			Restoran r = new Restoran();
			//u for petlji dobijarmo restoran r u kojem radi prosledjeni menadzer
			for(Restoran tempRestoran : restorani.values()) {
				if(tempRestoran.getName().equals(restaurantName)) {
					r = tempRestoran;
					break;
				}
			}
			
			for(Artikal tempArt : r.getMenuItems()) {
				if(a.getNaziv().equals(tempArt.getNaziv())) {
					//vec postoji taj artikal
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
