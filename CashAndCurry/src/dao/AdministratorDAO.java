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

import model.Administrator;
import model.Customer;

public class AdministratorDAO {

	private Map<String, Administrator> administrators = new HashMap<>();
	private String contextPath;
	public AdministratorDAO() {
	}
	public AdministratorDAO(String contextPath) {
		this.contextPath = contextPath;
		loadAdministrators(contextPath);
	}
	public void loadAdministrators(String contextPath) {
		BufferedReader in = null;
		try {
		/*	Gson gson = new Gson();
			File file = new File(contextPath + "administrators.json");
			in = new BufferedReader(new FileReader(file));
			ArrayList<Administrator> listOfAdministrators = new ArrayList<Administrator>();
			String line;
			while((line = in.readLine()) != null) {
				Administrator a = gson.fromJson(line, Administrator.class);
				listOfAdministrators.add(a);
				//dodajem i u mapu za svaki slucaj ako mapa bude zgodnija
				administrators.put(a.getUsername(), a);*/
			
			JsonReader reader = new JsonReader(new FileReader(contextPath + "administrators.json"));
			Gson gson = new Gson();
			Administrator[] tempAdministrators = gson.fromJson(reader, Administrator[].class);
			for(Administrator c : tempAdministrators) {
				administrators.put(c.getUsername(), c);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	public Collection<Administrator> findAllAdministrators(){
		return administrators.values();
	}
	public Administrator findAdministrator(String username) {
		return administrators.containsKey(username) ? administrators.get(username) : null;
	}
	
	public Administrator addNewAdministrator(Administrator administrator) {
		if(!administrators.containsKey(administrator.getUsername())) {
			administrators.put(administrator.getUsername(), administrator);
			Gson gson = new Gson();
			String temp = gson.toJson(administrators);
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "administrators.json", true))){
				System.out.println("Upis novog administratora u bazu.");
				bw.append(temp);
				bw.append("\n");
				bw.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
			return administrator;
		}	
		return null;
	}
	
	//izmena postojeceg korisnika i njegovo cuvanje u bazu
		public Administrator updateAdministrator(Administrator administrator) {
			if(administrators.containsKey(administrator.getUsername())) {
				administrators.replace(administrator.getUsername(), administrator);
				Gson gson = new Gson();
				String temp = gson.toJson(administrators);
				try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "administrators.json", true))){
					bw.append(temp);
					bw.append("\n");
					bw.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
				return administrator;
			}
			return null;
		}
		
		//brisanje korisnika iz baze
		public Administrator removeAdministrator(Administrator administrator) {
			if(administrators.containsKey(administrator.getUsername())) {
				administrators.remove(administrator.getUsername());
				Gson gson = new Gson();
				String temp = gson.toJson(administrators);
				try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "administrators.json", true))){
					bw.append(temp);
					bw.append("\n");
					bw.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
				return administrator;
			}
			return null;
		}
	
}
