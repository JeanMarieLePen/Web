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
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import model.Administrator;
import model.Customer;
import model.Manager;
import model.Restoran;


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
	
	public int generateId(String naziv) {
		int generatedId = -1;
		if(managers.containsKey(naziv)) {
			ArrayList<Manager> listaManagera = (ArrayList<Manager>) this.managers.values().stream().collect(Collectors.toList());
			List<Integer> listIds = new ArrayList<Integer>();
			
			for(Manager m : listaManagera) {
				listIds.add(m.getId());
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
	
	public Collection<Manager> findAllManagers(){
		return managers.values();
	}
	public Manager findManager(String username) {
		return managers.containsKey(username) ? managers.get(username) : null;
	}
	
	public Collection<Manager> findFreeManagers() {
		Collection<Manager> tempLista = new ArrayList<Manager>();
		for(Manager m : managers.values()) {
			if(m.getRestaurant().equals("")) {
				tempLista.add(m);
			}
		}
		System.out.println("Broj slobodnih menadzera je: " + tempLista.size());
		
		return tempLista;
		
	}
	
	//dodavanje novog Customera i njegovo cuvanje u bazu
		public Manager addNewManager(Manager manager) {
			if(!managers.containsKey(manager.getUsername())) {
				managers.put(manager.getUsername(), manager);
				managers.get(manager.getUsername()).setId(this.generateId(manager.getUsername()));
				Gson gson = new Gson();
				//String temp = gson.toJson(managers);
				
				Collection<Manager> tmp = this.managers.values();
				String fileInput = gson.toJson(tmp);
				
				
				
				System.out.println("Lokacija upisivanja u fajl: " + contextPath);
				try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "managers.json", false))){
					System.out.println("string koji se unosi u managers.json: " + fileInput);
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
//				managers.replace(manager.getUsername(), manager);
				Manager c = this.managers.get(manager.getUsername());
				//menjam mu polja
				c.setName(manager.getName()); c.setLastname(manager.getLastname());
				c.setDateOfBirth(manager.getDateOfBirth()); c.setPassword(manager.getPassword());
				c.setGender(manager.getGender());
				
				Gson gson = new Gson();
				String temp = gson.toJson(managers.values());
				try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "managers.json", false))){
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
				try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "managers.json", false))){
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
		
		public boolean checkPassword(String tempPassword, String username) {
			System.out.println("Provera sifri");
			if(this.managers.containsKey(username)) {
				String tempSifra = this.managers.get(username).getPassword();
				if(tempSifra.equals(tempPassword)) {
					return true;
				}
			}
			return false;
		}

}
