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
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import model.Customer;
import model.Manager;


public class ManagerDAO {
	
	private Map<String, Manager> managers = new HashMap<>();
	private String contextPath;
	
	public ManagerDAO() {
		
	}
	
	public ManagerDAO(String contextPath) {
		this.contextPath = contextPath;
		loadManagers(contextPath);
	}
	
	public void loadManagers(String contextPath) {
		BufferedReader in = null;
		try {
			
			JsonReader reader = new JsonReader(new FileReader(contextPath + "managers.json"));
			Gson gson = new Gson();
			Manager[] tempManagers = gson.fromJson(reader, Manager[].class);
			if(tempManagers != null) {
				for(Manager c : tempManagers) {
					managers.put(c.getUsername(), c);
				}
			}

		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	public Collection<Manager> findAllManagers(){
		return managers.values();
	}
	public Manager findManager(String username) {
		return managers.containsKey(username) ? managers.get(username) : null;
	}
	
	public Collection<Manager> findFreeManagers() {
		Collection<Manager> tempLista =new ArrayList<Manager>();
		for(Manager m : managers.values()) {
			if(m.getRestaurant().equals("")) {
				tempLista.add(m);
			}
		}
		
		
		return tempLista;
		
	}
	
	//dodavanje novog Customera i njegovo cuvanje u bazu
		public Manager addNewManager(Manager manager) {
			if(!managers.containsKey(manager.getUsername())) {
				managers.put(manager.getUsername(), manager);
				Gson gson = new Gson();
				String temp = gson.toJson(managers);
				
				Collection<Manager> tmp = this.managers.values();
				String fileInput = gson.toJson(tmp);
				
				
				
				System.out.println("Lokacija upisivanja u fajl: " + contextPath);
				try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "managers.json", false))){
					System.out.println("Upis novog menadzera u bazu.");
					bw.append(fileInput);
					bw.append("\n");
					bw.close();
//				try{
//					FileWriter fileWriter = new FileWriter(contextPath + "managers.json", true);
//					Collection<Manager> tmp = this.managers.values();
//					String fileInput = gson.toJson(tmp);
//					System.out.println("ONO STO CE SE UPISATI U FAJL: " + fileInput);
//					gson.toJson(tmp, fileWriter);
				}catch(IOException e) {
					e.printStackTrace();
				}
				return manager;
			}	
			return null;
		}
		
		//izmena postojeceg korisnika i njegovo cuvanje u bazu
		public Manager updateManager(Manager manager) {
			if(managers.containsKey(manager.getUsername())) {
				managers.replace(manager.getUsername(), manager);
				Gson gson = new Gson();
				String temp = gson.toJson(managers);
				try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "managers.json", true))){
					bw.append(temp);
					bw.append("\n");
					bw.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
				return manager;
			}
			return null;
		}
		
		//brisanje korisnika iz baze
		public Manager removeManager(Manager manager) {
			if(managers.containsKey(manager.getUsername())) {
				managers.remove(manager.getUsername());
				Gson gson = new Gson();
				String temp = gson.toJson(managers);
				try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "managers.json", true))){
					bw.append(temp);
					bw.append("\n");
					bw.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
				return manager;
			}
			return null;
		}

}
