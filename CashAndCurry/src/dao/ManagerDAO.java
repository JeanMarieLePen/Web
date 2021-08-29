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

import model.Manager;


public class ManagerDAO {
	
	private Map<String, Manager> managers = new HashMap<>();
	public ManagerDAO() {
		
	}
	
	public ManagerDAO(String contextPath) {
		loadManagers(contextPath);
	}
	
	public void loadManagers(String contextPath) {
		BufferedReader in = null;
		try {
			Gson gson = new Gson();
			File file = new File(contextPath + "managers.json");
			in = new BufferedReader(new FileReader(file));
			ArrayList<Manager> listOfManagers = new ArrayList<Manager>();
			String line;
			while((line = in.readLine()) != null) {
				Manager m = gson.fromJson(line, Manager.class);
				listOfManagers.add(m);
				managers.put(m.getUsername(), m);
			}
			

		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if(in != null) {
				try {
					in.close();
				}
				catch(Exception e) {
					
				}
			}
		}
	}
	public Collection<Manager> findAllManagers(){
		return managers.values();
	}
	public Manager findManager(String username) {
		return managers.containsKey(username) ? managers.get(username) : null;
	}
	public Manager save(Manager manager) {
		managers.put(manager.getUsername(), manager);
		Gson gson = new Gson();
		String temp = gson.toJson(manager);
		try(BufferedWriter bw = new BufferedWriter(new FileWriter("managers.json", true))){
			System.out.println("Upis novog menadzera u bazu.");
			bw.append(temp);
			bw.append("\n");
			bw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return manager;
	}

}
