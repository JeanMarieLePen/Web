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

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

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
			return restorani.containsKey(name) ? restorani.get(name) : null;
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
				try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "restorani.json", true))){
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
				try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "restorani.json", true))){
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
		
		public Collection<Restoran> findAllRestaurantsWithNoManagers(){
			Collection<Restoran> tmp = new ArrayList<Restoran>();
			for(Restoran r : restorani.values()) {
				if(r.getManager().getUsername().equals("")) {
					tmp.add(r);
				}
			}
			return tmp;
		}
}
