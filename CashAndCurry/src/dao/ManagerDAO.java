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
		/*	Gson gson = new Gson();
			File file = new File(contextPath + "managers.json");
			in = new BufferedReader(new FileReader(file));
			ArrayList<Manager> listOfManagers = new ArrayList<Manager>();
			String line;
			while((line = in.readLine()) != null) {
				Manager m = gson.fromJson(line, Manager.class);
				listOfManagers.add(m);
				managers.put(m.getUsername(), m);  */
			
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
	
	//dodavanje novog Customera i njegovo cuvanje u bazu
		public Manager addNewManager(Manager manager) {
			if(!managers.containsKey(manager.getUsername())) {
				managers.put(manager.getUsername(), manager);
				Gson gson = new Gson();
				String temp = gson.toJson(managers);
				try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "managers.json", false))){
					System.out.println("Upis novog menadzera u bazu.");
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
